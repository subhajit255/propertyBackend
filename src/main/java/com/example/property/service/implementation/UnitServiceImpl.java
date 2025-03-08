package com.example.property.service.implementation;

import com.example.property.model.Building;
import com.example.property.model.Unit;
import com.example.property.repository.BuildingRepository;
import com.example.property.repository.UnitRepository;
import com.example.property.request.UnitAddRequest;
import com.example.property.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    UnitRepository unitRepository;
    @Override
    public Unit addUnit(UnitAddRequest unitAddRequest) {
        Unit unit = new Unit();
        // get building
        Building building = this.getBuilding(unitAddRequest.getBuilding_id());
        if(building != null) {
            unit.setBuilding(building);
            unit.setName(unitAddRequest.getName());
            unit.setDetails(unitAddRequest.getDetails());
            unit.setBeds(unitAddRequest.getBeds());
            unit.setBaths(unitAddRequest.getBaths());
            unit.setSize(unitAddRequest.getSize());
            unit.setMarketRent(unitAddRequest.getMarket_rent());
            // save into database
            return unitRepository.save(unit);
        }
        return null;
    }

    @Override
    public Unit getUnitDetails(UUID id) {
        return null;
    }

    @Override
    public Unit updateUnit(UnitAddRequest unitAddRequest, UUID id) {
        return null;
    }

    @Override
    public boolean deleteUnit(UUID id) {
        return false;
    }

    private Building getBuilding(UUID buildingId){
        Optional<Building> isBuildingExist = buildingRepository.findById(buildingId);
        if(isBuildingExist.isPresent()){
            return isBuildingExist.get();
        }
        return null;
    }
}
