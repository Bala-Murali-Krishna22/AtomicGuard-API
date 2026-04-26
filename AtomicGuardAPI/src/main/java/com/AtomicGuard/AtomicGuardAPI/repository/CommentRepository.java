package com.AtomicGuard.AtomicGuardAPI.repository;

import com.AtomicGuard.AtomicGuardAPI.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
