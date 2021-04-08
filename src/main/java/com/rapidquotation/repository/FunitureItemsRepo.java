package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.FurnitureItems;

@Repository
public interface FunitureItemsRepo extends JpaRepository<FurnitureItems, Integer> {

}
