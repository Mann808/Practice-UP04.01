package com.mpt.journal.service;

import com.mpt.journal.entity.UserEntity;
import com.mpt.journal.entity.RoleEntity;
import java.util.Set;

public interface UserService {
    UserEntity findByUsername(String username);
    void save(UserEntity user);
    void saveAdmin(UserEntity user, Set<RoleEntity> roles);
}