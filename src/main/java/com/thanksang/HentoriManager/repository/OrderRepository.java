package com.thanksang.HentoriManager.repository;

import com.thanksang.HentoriManager.config.EnumType.OrderStatus;
import com.thanksang.HentoriManager.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    @Query("select o from OrderEntity o join fetch o.clientEntity c join fetch o.receivableEntity r join fetch o.userEntity u")
    Page<OrderEntity> findAllOrders(Pageable pageable);

    @Query("select o from OrderEntity o join fetch o.clientEntity c join fetch o.receivableEntity r join fetch o.userEntity u where r.status = :status")
    Page<OrderEntity> findAllReceivable(Boolean status ,Pageable pageable);

    @Query("select o from OrderEntity o join fetch o.clientEntity c join fetch o.receivableEntity r join fetch o.userEntity u where o.appointment < :localDate and  o.orderStatusEnum <> :orderStatusEnum")
    Page<OrderEntity> findAllOverDue(LocalDate localDate, Pageable pageable, OrderStatus orderStatusEnum);

    Page<OrderEntity> findAllByOrderStatusEnum(OrderStatus orderStatusEnum, Pageable pageable);
}
