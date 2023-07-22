package com.springsecurity.securityjwt.service;

import com.springsecurity.securityjwt.entity.Role;
import com.springsecurity.securityjwt.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;
    public Role createNewRole(Role role){
        return roleRepo.save(role);
    }
}
