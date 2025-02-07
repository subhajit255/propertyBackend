package com.example.property.service;

import com.example.property.model.Role;
import com.example.property.repository.RoleRepository;
import com.example.property.request.RoleRequest;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleService {

    // get all except super-admin
    List<Role> getAll();
    // find By uuid
    Role findByUUid(UUID id);

    // store data
    Role add(RoleRequest roleRequest);

    // Role update
    Role update(RoleRequest roleRequest,UUID id);

    // delete role
    void delete(UUID id);

    List<Role> getRoleByIds(Set<UUID> ids);
}
