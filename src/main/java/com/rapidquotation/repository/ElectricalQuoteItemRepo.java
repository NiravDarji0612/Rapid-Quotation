package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.ElectricalQuoteItems;

@Repository
public interface ElectricalQuoteItemRepo extends JpaRepository<ElectricalQuoteItems, Integer> {

}
