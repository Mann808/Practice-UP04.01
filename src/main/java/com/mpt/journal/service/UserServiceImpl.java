package com.mpt.journal.service;

import com.mpt.journal.entity.UserEntity;
import com.mpt.journal.entity.RoleEntity;
import com.mpt.journal.repository.UserRepository;
import com.mpt.journal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        RoleEntity userRole = roleRepository.findByName("ROLE_USER");
        if(userRole == null){
            userRole = new RoleEntity("ROLE_USER");
            roleRepository.save(userRole);
        }
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void saveAdmin(UserEntity user, Set<RoleEntity> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<RoleEntity> userRoles = new HashSet<>();
        for(RoleEntity role : roles){
            RoleEntity existingRole = roleRepository.findByName(role.getName());
            if(existingRole == null){
                existingRole = new RoleEntity(role.getName());
                roleRepository.save(existingRole);
            }
            userRoles.add(existingRole);
        }
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    // Initialize default roles and admin user
    @PostConstruct
    public void init() {
        RoleEntity userRole = roleRepository.findByName("ROLE_USER");
        if(userRole == null){
            userRole = new RoleEntity("ROLE_USER");
            roleRepository.save(userRole);
        }

        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN");
        if(adminRole == null){
            adminRole = new RoleEntity("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        // Create default admin user if not exists
        if(userRepository.findByUsername("admin") == null){
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword("Admin@123"); // This will be encoded in saveAdmin
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(adminRole);
            saveAdmin(admin, roles);
        }
    }
}