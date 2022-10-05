package com.bobocode.creditadvisory.service;

import com.bobocode.creditadvisory.domain.Advisor;
import com.bobocode.creditadvisory.domain.Application;
import com.bobocode.creditadvisory.exception.AssignException;
import com.bobocode.creditadvisory.repository.AdvisorRepository;
import com.bobocode.creditadvisory.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppService {

    private final AdvisorRepository advisorRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
//    @Retryable(backoff = @Backoff(2000))
    public Application assign(Long id) {
        Objects.requireNonNull(id);
        var advisor = findAdvisor(id);

        var application =
                applicationRepository
                        .findTopByStatusAndAmountBetweenOrderByCreatedAt(
                                Application.Status.NEW,
                                Advisor.Role.valueOf(advisor.getRole().name()).getFrom(),
                                Advisor.Role.valueOf(advisor.getRole().name()).getTo())
                        .orElseThrow(() -> new AssignException("No available applications found"));

        log.info("Found application: {}", application);

        advisor.addApplication(application);
        application.changeToAssigned();

        log.info("Application: {} was assigned to advisor: {}", application, advisor);

        return application;
    }

    private Advisor findAdvisor(Long id) {
        var advisor = advisorRepository.findById(id)
                .orElseThrow(() -> new AssignException("Advisor with id=%d is not found".formatted(id)));

        log.info("Found advisor: {}", advisor);

        if (advisor.hasAssignedApplication())
            throw new AssignException("Advisor with id=%d already has assigned application".formatted(advisor.getId()));

        return advisor;
    }
}
