package com.thanksang.HentoriManager.repository;

import com.thanksang.HentoriManager.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
}
