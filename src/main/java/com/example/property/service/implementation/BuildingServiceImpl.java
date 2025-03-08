package com.example.property.service.implementation;

import com.example.property.model.Building;
import com.example.property.model.Property;
import com.example.property.repository.BuildingRepository;
import com.example.property.repository.PropertyRepository;
import com.example.property.request.BuildingRequest;
import com.example.property.request.BuildingUpdateRequest;
import com.example.property.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private BuildingRepository buildingRepository;


    @Override
    public boolean addBuilding(BuildingRequest buildingRequest) {
        Optional<Property> isPropertyExist = propertyRepository.findById(buildingRequest.getPropertyId());
        if (isPropertyExist.isPresent()){
            Property property = isPropertyExist.get();
            Building building = new Building();
            building.setProperty(property);
            building.setName(buildingRequest.getName());
            // save the data
            buildingRepository.save(building);
            return true;
        }
        return false;
    }

    @Override
    public List<Building> buildingsByProperty(UUID propertyId) {
        System.out.println("property_id ----->"+propertyId);
        List<Building> buildings =  buildingRepository.findBuildingByPropertyId(propertyId);
        System.out.println("buildings -------->"+buildings);
        return buildings;
    }

    @Override
    public Building updateBuilding(UUID buildingId, BuildingUpdateRequest buildingUpdateRequest) {
        Optional<Building> isBuildingExist = buildingRepository.findById(buildingId);
        if (isBuildingExist.isPresent()){
            Building building = isBuildingExist.get();
            // set data
            Property isProperty = this.findProperty(buildingUpdateRequest.getPropertyId());
            if(isProperty != null){
                building.setProperty(isProperty);
                building.setName(buildingUpdateRequest.getName());
                return buildingRepository.save(building);
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean deleteBuilding(UUID buildingId) {
        Optional<Building> isBuildingExist = buildingRepository.findById(buildingId);
        if (isBuildingExist.isPresent()) {
            Building building = isBuildingExist.get();
            buildingRepository.delete(building);
            return true;
        }
        return false;
    }

    private Property findProperty(UUID propertyId){
        Optional<Property> isPropertyExist = propertyRepository.findById(propertyId);
        if(isPropertyExist.isPresent()){
            Property property = isPropertyExist.get();
            return property;
        }
        return null;
    }
}
