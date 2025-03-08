package com.example.property.repository;

import com.example.property.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {
    @Query("SELECT b FROM Building b WHERE b.property.id = :propertyId")
    List<Building> findBuildingByPropertyId(@Param("propertyId") UUID propertyId);
}
