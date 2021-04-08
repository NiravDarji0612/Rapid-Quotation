package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.DecorationItems;

@Repository
public interface DecorationItemsRepo extends JpaRepository<DecorationItems, Integer> {

}
