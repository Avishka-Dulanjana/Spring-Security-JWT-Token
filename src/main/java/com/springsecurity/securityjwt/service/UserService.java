package com.springsecurity.securityjwt.service;

import com.springsecurity.securityjwt.entity.Role;
import com.springsecurity.securityjwt.entity.User;
import com.springsecurity.securityjwt.repo.RoleRepo;
import com.springsecurity.securityjwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;
    public User registerNewUser(User user) {
        return userRepo.save(user);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {
        Role adminRole = new Role();
        Role userRole = new Role();

        if (!roleRepo.existsById("Admin")) {
            adminRole.setRoleName("Admin");
            adminRole.setRoleDiscription("Admin Role");
            roleRepo.save(adminRole);

        }
        if (!roleRepo.existsById("User")) {
            userRole.setRoleName("User");
            userRole.setRoleDiscription("User Role");
            roleRepo.save(userRole);

        }

        if (!userRepo.existsById("admin123")) {
            User user = new User();
            user.setUserName("admin123");
            user.setUsePassword(getEncodedPassword("admin@123"));
            user.setUserFirstName("Avishka");
            user.setUserLastName("Dulanjana");

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            user.setRole(adminRoles);
            userRepo.save(user);
        }
        if (!userRepo.existsById("user123")) {
            User user = new User();
            user.setUserName("user123");
            user.setUsePassword(getEncodedPassword("user@123"));
            user.setUserFirstName("Sakithma");
            user.setUserLastName("Gamage");

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            user.setRole(userRoles);
            userRepo.save(user);
        }
    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
