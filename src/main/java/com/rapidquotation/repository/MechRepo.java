package com.rapidquotation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.mechanical.MechanicalDesignQuote;

@Repository
public interface MechRepo extends JpaRepository<MechanicalDesignQuote, Integer> {

}
