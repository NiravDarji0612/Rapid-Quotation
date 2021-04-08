package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.mechanical.DecorationQuote;

@Repository
public interface DecorationQuoteRepo extends JpaRepository<DecorationQuote, Integer> {
	
	
}
