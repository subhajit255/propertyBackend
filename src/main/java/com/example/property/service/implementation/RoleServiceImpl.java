package com.example.property.service.implementation;

import com.example.property.model.Role;
import com.example.property.repository.RoleRepository;
import com.example.property.request.RoleRequest;
import com.example.property.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByUUid(UUID id) {
        return roleRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("no role found with this id "+ id)
        );
    }

    @Override
    public Role add(RoleRequest roleRequest) {
        Role role = new Role();
        role.setName(roleRequest.getName());
        return roleRepository.save(role);
    }

    @Override
    public Role update(RoleRequest roleRequest, UUID id) {
        Optional<Role> isExistingRole = roleRepository.findById(id);

        Role existingRole = isExistingRole.get();

        // Check if there are any users associated with the role
        if (existingRole.getUsers() != null && !existingRole.getUsers().isEmpty()) {
            throw new IllegalArgumentException("Cannot update the role as users are associated with it");
        }
        existingRole.setName(roleRequest.getName());
        return roleRepository.save(existingRole);
    }

    @Override
    public void delete(UUID id) {
        Optional<Role> isRole = roleRepository.findById(id);

        Role role = isRole.get();

        // Check if there are any users associated with this role
        if (role.getUsers() != null && !role.getUsers().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete the role as users are associated with it");
        }
        roleRepository.delete(role);
    }

    @Override
    public List<Role> getRoleByIds(Set<UUID> ids) {
        List<Role> roles = roleRepository.findAllById(ids);
        if(roles.size() != ids.size()){
            throw new EntityNotFoundException("some role not found using the id");
        }
        return roles;
    }
}
