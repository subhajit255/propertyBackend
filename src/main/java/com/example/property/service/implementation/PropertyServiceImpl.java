package com.example.property.service.implementation;

import com.example.property.model.*;
import com.example.property.repository.CountryRepository;
import com.example.property.repository.PropertyRepository;
import com.example.property.repository.StateRepository;
import com.example.property.request.PropertyRequest;
import com.example.property.service.PropertyKindService;
import com.example.property.service.PropertyService;
import com.example.property.service.PropertyTypeService;
import com.example.property.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyKindService propertyKindService;
    @Autowired
    private PropertyTypeService propertyTypeService;
    @Autowired
    private UserService userService;

    @Override
    public List<Property>  getPropertyByUser(UUID userId) {
        return propertyRepository.findPropertyByUser(userId);
    }

    @Override
    public Property add(UUID userId, PropertyRequest propertyRequest) {
        // get property kind details
        PropertyKind propertyKind = propertyKindService.getById(propertyRequest.getPropertyKindId());
        // get property type details
        PropertyType propertyType = propertyTypeService.getById(propertyRequest.getPropertyTypeId());
        // get user details
        User user = userService.getUserById(userId);
        Property property = new Property();
        property.setPropertyKind(propertyKind);
        property.setPropertyType(propertyType);
        property.setName(propertyRequest.getName());
        property.setDescription(propertyRequest.getDescription());
        property.setLatitude(propertyRequest.getLatitude());
        property.setLongitude(propertyRequest.getLongitude());
        property.setStreetAddress1(propertyRequest.getStreetAddress1());
        property.setStreetAddress2(propertyRequest.getStreetAddress2());
        property.setCity(propertyRequest.getCity());
        property.setState(this.getState(propertyRequest.getStateId()));
        property.setCountry(this.getCountry(propertyRequest.getCountryId()));
        property.setZipCode(propertyRequest.getZipCode());
        property.setBankName(propertyRequest.getBankName());
        property.setBranchName(propertyRequest.getBranchName());
        property.setAccNo(propertyRequest.getAccNo());
        property.setRoutingNo(propertyRequest.getRoutingNo());
        property.setUser(user);
        // save data
        return propertyRepository.save(property);
    }

    @Override
    public Property update(UUID propertyId, PropertyRequest propertyRequest) {
        // Check if the property exists
        Property existingProperty = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));

        // Get property kind and type details
        PropertyKind propertyKind = propertyKindService.getById(propertyRequest.getPropertyKindId());
        PropertyType propertyType = propertyTypeService.getById(propertyRequest.getPropertyTypeId());

        // Update the existing property object
        existingProperty.setPropertyKind(propertyKind);
        existingProperty.setPropertyType(propertyType);
        existingProperty.setName(propertyRequest.getName());
        existingProperty.setDescription(propertyRequest.getDescription());
        existingProperty.setLatitude(propertyRequest.getLatitude());
        existingProperty.setLongitude(propertyRequest.getLongitude());
        existingProperty.setStreetAddress1(propertyRequest.getStreetAddress1());
        existingProperty.setStreetAddress2(propertyRequest.getStreetAddress2());
        existingProperty.setCity(propertyRequest.getCity());
        existingProperty.setState(this.getState(propertyRequest.getStateId()));
        existingProperty.setCountry(this.getCountry(propertyRequest.getCountryId()));
        existingProperty.setZipCode(propertyRequest.getZipCode());
        existingProperty.setBankName(propertyRequest.getBankName());
        existingProperty.setBranchName(propertyRequest.getBranchName());
        existingProperty.setAccNo(propertyRequest.getAccNo());
        existingProperty.setRoutingNo(propertyRequest.getRoutingNo());

        // Save and return the updated property
        return propertyRepository.save(existingProperty);
    }


    @Override
    public boolean delete(UUID propertyId) {
        // Check if the property exists
        Optional<Property> isPropertyExist = propertyRepository.findById(propertyId);
        if(isPropertyExist.isPresent()){
            Property property = isPropertyExist.get();
            propertyRepository.delete(property);
            return true;
        }
        return false;
    }

    @Override
    public State getState(int stateId) {
        Optional<State> isState = stateRepository.findById(stateId);
        if(isState.isPresent()){
            State state = isState.get();
            return state;
        }
        return null;
    }

    @Override
    public Country getCountry(int countryId) {
        Optional<Country> isCountry = countryRepository.findById(countryId);
        if(isCountry.isPresent()){
            Country country = isCountry.get();
            return country;
        }
        return null;
    }

}
