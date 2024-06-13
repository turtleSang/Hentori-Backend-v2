package com.thanksang.HentoriManager.repository;

import com.thanksang.HentoriManager.entity.ReceivableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivableRepository extends JpaRepository<ReceivableEntity, String> {
}
