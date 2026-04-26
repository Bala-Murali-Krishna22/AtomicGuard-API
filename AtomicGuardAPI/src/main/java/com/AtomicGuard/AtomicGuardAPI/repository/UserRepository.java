package com.AtomicGuard.AtomicGuardAPI.repository;

import com.AtomicGuard.AtomicGuardAPI.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
