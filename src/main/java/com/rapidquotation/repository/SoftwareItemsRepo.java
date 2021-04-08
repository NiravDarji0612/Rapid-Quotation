package com.rapidquotation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquotation.entities.SoftwareDevelopmentItems;

@Repository
public interface SoftwareItemsRepo extends JpaRepository<SoftwareDevelopmentItems, Integer> {

}
