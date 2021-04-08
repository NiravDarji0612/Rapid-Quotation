package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.mechanical.SoftwareDevelopmentQuote;

@Repository
public interface SoftwareDevelopmentRepo extends JpaRepository<SoftwareDevelopmentQuote, Integer> {

}
