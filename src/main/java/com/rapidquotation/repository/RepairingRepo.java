package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.mechanical.RepairingQuotation;

@Repository
public interface RepairingRepo extends JpaRepository<RepairingQuotation,Integer> {

}
