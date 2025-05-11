package com.hospital.Service;

import com.hospital.Entity.Role;
import com.hospital.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}