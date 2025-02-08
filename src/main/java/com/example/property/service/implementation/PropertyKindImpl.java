package com.example.property.service.implementation;

import com.example.property.model.PropertyKind;
import com.example.property.repository.PropertyKindRepository;
import com.example.property.service.PropertyKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class PropertyKindImpl implements PropertyKindService {
    @Autowired
    private PropertyKindRepository propertyKindRepository;

    @Override
    public List<PropertyKind> getAllPropertyKinds() {
        return propertyKindRepository.findAll();
    }

    @Override
    public PropertyKind add(PropertyKind propertyKind) {
        return propertyKindRepository.save(propertyKind);
    }

    @Override
    public PropertyKind getById(UUID id) {
        Optional<PropertyKind> isPropertyKind = propertyKindRepository.findById(id);
        if(isPropertyKind.isPresent()){
            PropertyKind propertyKind = isPropertyKind.get();
            return propertyKind;
        }
        return null;
    }

    @Override
    public PropertyKind update(UUID id, PropertyKind propertyKind) {
        PropertyKind isExistPropertyKind = this.getById(id);
        if(isExistPropertyKind != null){
            return propertyKindRepository.save(propertyKind);
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        PropertyKind isExistPropertyKind = this.getById(id);
        if(isExistPropertyKind != null){
            propertyKindRepository.delete(isExistPropertyKind);
        }
    }
}
