package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.RepairingQuotationItems;

@Repository
public interface RepairingItemsRepo extends JpaRepository<RepairingQuotationItems, Integer> {

}
