package com.AtomicGuard.AtomicGuardAPI.repository;

import com.AtomicGuard.AtomicGuardAPI.entity.BotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotRepository extends JpaRepository<BotEntity, Long> {

}
