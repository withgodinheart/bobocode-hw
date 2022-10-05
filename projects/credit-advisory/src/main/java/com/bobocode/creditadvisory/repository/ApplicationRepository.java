package com.bobocode.creditadvisory.repository;

import com.bobocode.creditadvisory.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findTopByStatusAndAmountBetweenOrderByCreatedAt(Application.Status status, BigDecimal from, BigDecimal to);
}
