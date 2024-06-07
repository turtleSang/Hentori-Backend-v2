package com.thanksang.HentoriManager.repository;

import com.thanksang.HentoriManager.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, String> {
    Optional<ClientEntity> findByPhoneNumber(String phoneNumber);

    @Query(
            value = "select * from hentori.clients where hentori.clients.name like :name ",
            nativeQuery = true
    )
    List<ClientEntity> findAllByName(String name, Pageable page);
}
