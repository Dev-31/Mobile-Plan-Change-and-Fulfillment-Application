package com.prodapt.mobileplan.repository;

import com.prodapt.mobileplan.entity.AIMemory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIMemoryRepository extends JpaRepository<AIMemory, Long> {

    List<AIMemory> findTop5ByUserIdOrderByCreatedAtDesc(Long userId);
}
