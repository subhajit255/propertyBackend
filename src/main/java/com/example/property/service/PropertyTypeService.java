package com.example.property.service;

import com.example.property.model.PropertyType;

import java.util.List;
import java.util.UUID;

public interface PropertyTypeService {

    List<PropertyType> getAll();

    PropertyType getById(UUID id);

    PropertyType add(PropertyType propertyType);

    PropertyType update(PropertyType propertyType, UUID id);

    boolean delete(UUID id);

}
