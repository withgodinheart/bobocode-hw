package com.bobocode.creditadvisory.repository;

import com.bobocode.creditadvisory.domain.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {
}
