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
        return null;
    }

    @Override
    public boolean delete(UUID propertyId) {
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
