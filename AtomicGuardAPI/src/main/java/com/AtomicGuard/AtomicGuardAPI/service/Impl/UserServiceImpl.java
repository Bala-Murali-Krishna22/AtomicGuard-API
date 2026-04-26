package com.AtomicGuard.AtomicGuardAPI.service.Impl;

import com.AtomicGuard.AtomicGuardAPI.entity.UserEntity;
import com.AtomicGuard.AtomicGuardAPI.repository.UserRepository;
import com.AtomicGuard.AtomicGuardAPI.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepo.save(user);
    }

}
