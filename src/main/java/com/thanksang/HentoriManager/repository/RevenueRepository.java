package com.thanksang.HentoriManager.repository;

import com.thanksang.HentoriManager.entity.RevenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<RevenueEntity, Integer> {
}
