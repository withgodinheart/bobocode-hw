package com.bobocode.creditadvisory.repository;

import com.bobocode.creditadvisory.domain.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}
