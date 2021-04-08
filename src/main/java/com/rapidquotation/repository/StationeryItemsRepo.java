package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.StationeriesItems;

@Repository
public interface StationeryItemsRepo extends JpaRepository<StationeriesItems, Integer> {

}
