package com.example.property.service;

import com.example.property.model.Building;
import com.example.property.request.BuildingRequest;
import com.example.property.request.BuildingUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface BuildingService {
    boolean addBuilding(BuildingRequest buildingRequest);
    List<Building> buildingsByProperty(UUID propertyId);
    Building updateBuilding(UUID buildingId, BuildingUpdateRequest buildingUpdateRequest);
    boolean deleteBuilding(UUID buildingId);
}
