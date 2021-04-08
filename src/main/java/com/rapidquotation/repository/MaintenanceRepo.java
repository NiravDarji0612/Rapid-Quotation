package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.mechanical.MaintenanceQuotation;

@Repository
public interface MaintenanceRepo extends JpaRepository<MaintenanceQuotation, Integer> {

}
