package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.MaintenanceQuotationItems;

@Repository
public interface MaintenanceItemsRepo extends JpaRepository<MaintenanceQuotationItems, Integer> {

}
