package com.example.property.service;

import com.example.property.model.Unit;
import com.example.property.request.UnitAddRequest;

import java.util.UUID;

public interface UnitService {
    Unit addUnit(UnitAddRequest unitAddRequest);
    Unit getUnitDetails(UUID id);
    Unit updateUnit(UnitAddRequest unitAddRequest, UUID id);
    boolean deleteUnit(UUID id);
}
