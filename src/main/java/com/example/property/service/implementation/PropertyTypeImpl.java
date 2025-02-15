package com.example.property.service.implementation;

import com.example.property.model.PropertyType;
import com.example.property.repository.PropertyTypeRepository;
import com.example.property.service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyTypeImpl implements PropertyTypeService {
    @Autowired
    PropertyTypeRepository propertyTypeRepository;

    @Override
    public List<PropertyType> getAll() {
        List<PropertyType> propertyTypes = propertyTypeRepository.findAll();
        return propertyTypes;
    }

    @Override
    public PropertyType getById(UUID id) {
        Optional<PropertyType> isPropertyType = propertyTypeRepository.findById(id);
        if(isPropertyType.isPresent()){
            PropertyType propertyType = isPropertyType.get();
            return propertyType;
        }
        return null;
    }

    @Override
    public PropertyType add(PropertyType propertyType) {
        return propertyTypeRepository.save(propertyType);
    }

    @Override
    public PropertyType update(PropertyType propertyType, UUID id) {
        PropertyType isPropertyType = this.getById(id);
        if(isPropertyType != null){
            return propertyTypeRepository.save(propertyType);
        }
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        PropertyType propertyType = this.getById(id);
        if(propertyType != null){
            propertyTypeRepository.delete(propertyType);
            return true;
        }
        return false;
    }
}
