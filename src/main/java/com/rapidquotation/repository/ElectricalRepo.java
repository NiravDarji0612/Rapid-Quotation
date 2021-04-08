package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.mechanical.ElectricalQuote;

@Repository
public interface ElectricalRepo extends JpaRepository<ElectricalQuote, Integer> {

}
