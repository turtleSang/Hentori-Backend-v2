package com.thanksang.HentoriManager.repository;

import com.thanksang.HentoriManager.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

}
