package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {

}
