package com.example.property.service;

import com.example.property.model.Country;
import com.example.property.model.Property;
import com.example.property.model.State;
import com.example.property.model.User;
import com.example.property.request.PropertyRequest;
import org.springframework.security.core.parameters.P;

import java.util.UUID;

public interface PropertyService {

    public Property add(UUID userId, PropertyRequest propertyRequest);

    public Property update(UUID propertyId, PropertyRequest propertyRequest);

    public boolean delete(UUID propertyId);

    public State getState(int stateId);

    public Country getCountry(int countryId);

}
