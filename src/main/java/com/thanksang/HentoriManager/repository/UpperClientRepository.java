package com.thanksang.HentoriManager.repository;

import com.thanksang.HentoriManager.entity.UpperClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpperClientRepository extends JpaRepository<UpperClientEntity, String> {

}
