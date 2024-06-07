package com.thanksang.HentoriManager.repository;

import com.thanksang.HentoriManager.entity.BelowClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BelowClientRepository extends JpaRepository<BelowClientEntity, String> {

}
