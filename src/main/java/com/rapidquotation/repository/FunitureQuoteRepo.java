package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.mechanical.FurnitureQuote;

@Repository
public interface FunitureQuoteRepo extends JpaRepository<FurnitureQuote, Integer> {

}
