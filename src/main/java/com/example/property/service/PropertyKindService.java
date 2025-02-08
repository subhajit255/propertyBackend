package com.example.property.service;


import com.example.property.model.PropertyKind;
import com.example.property.repository.PropertyKindRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyKindService {

    List<PropertyKind> getAllPropertyKinds();

    PropertyKind add(PropertyKind propertyKind);

    PropertyKind getById(UUID id);

    PropertyKind update(UUID id,PropertyKind propertyKind);

    void delete(UUID id);

}
