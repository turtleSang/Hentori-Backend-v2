package com.thanksang.HentoriManager.services;

import com.thanksang.HentoriManager.config.Constance;
import com.thanksang.HentoriManager.config.OrderStatusEnum;
import com.thanksang.HentoriManager.entity.*;
import com.thanksang.HentoriManager.error.OrderErrors;
import com.thanksang.HentoriManager.payload.ItemRequest;
import com.thanksang.HentoriManager.payload.OrderRequest;
import com.thanksang.HentoriManager.repository.*;
import com.thanksang.HentoriManager.services.Imp.OrderServiceImp;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class OrderService implements OrderServiceImp {
    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private OrderRepository orderRepository;
    private ReceivableRepository receivableRepository;
    private ModelMapper modelMapper;


    public OrderService(ClientRepository clientRepository,
                        UserRepository userRepository,
                        ItemRepository itemRepository,
                        OrderRepository orderRepository,
                        ReceivableRepository receivableRepository,
                        ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.receivableRepository = receivableRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void createOrder(OrderRequest orderRequest){
        try {
//            Check null field
            Constance.checkNullField(orderRequest);
//            Check users
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<UserEntity> userEntity = userRepository.findById(authentication.getPrincipal().toString());
            if (!userEntity.isPresent()) {
                throw new OrderErrors("User is not exits");
            }
//            Check client
            Optional<ClientEntity> clientEntity = clientRepository.findById(orderRequest.getClientID());
            if (!clientEntity.isPresent()) {
                throw new OrderErrors("Client is not exist");
            }
//            Check Status
            OrderStatusEnum status = Stream.of(OrderStatusEnum.values())
                    .filter(orderStatusEnum -> orderStatusEnum.getCode().equals(orderRequest.getStatus()))
                    .findFirst()
                    .orElseThrow(() -> new OrderErrors("Status not exits"));
//            Create create At
            LocalDate createAt = LocalDate.now();
//            Total items
            int total = totalItem(orderRequest.getItemRequestList());
//            Save order
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setClientEntity(clientEntity.get());
            orderEntity.setCreateAt(createAt);
            orderEntity.setOrderStatusEnum(status);
            orderEntity.setTotal(total);
            orderEntity = orderRepository.save(orderEntity);
            saveListItem(orderRequest.getItemRequestList(), orderEntity);
            saveReceivable(orderEntity);
        } catch (Exception e) {
            throw new OrderErrors(e.getMessage(), e.getCause());
        }

    }

    private int totalItem(List<ItemRequest> itemRequestList) {
        int total = 0;
        for (ItemRequest itemRequest : itemRequestList
        ) {
            total += itemRequest.getAmount() * itemRequest.getPrice();
        }
        return total;
    }

    private void saveListItem(List<ItemRequest> itemRequestList, OrderEntity orderEntity) {
        try {
            for (ItemRequest itemRequest : itemRequestList
            ) {
                int total = itemRequest.getAmount()*itemRequest.getPrice();
                ItemEntity itemEntity;
                switch (itemRequest.getClass().getSimpleName()){
                    case "ShirtRequest":
                        itemEntity = modelMapper.map(itemRequest, ShirtEntity.class);
                        break;
                    case "TrousersRequest":
                        itemEntity = modelMapper.map(itemRequest, TrousersEntity.class);

                        break;
                    case "SuitRequest":
                        itemEntity = modelMapper.map(itemRequest, SuitEntity.class);
                        break;
                    default:
                        itemEntity = modelMapper.map(itemRequest, DifferentItemEntity.class);
                        break;
                }
                itemEntity.setOrderEntity(orderEntity);
                itemEntity.setTotal(total);
                itemRepository.save(itemEntity);
            }
        } catch (Exception e){
            throw new OrderErrors(e.getMessage());
        }

    }

    private void saveReceivable(OrderEntity orderEntity){
        ReceivableEntity receivableEntity = new ReceivableEntity();
        receivableEntity.setClientEntity(orderEntity.getClientEntity());
        receivableEntity.setStatus(false);
        receivableEntity.setAmount(orderEntity.getTotal());
        receivableEntity.setRemaining(orderEntity.getTotal());
        receivableEntity.setPayment(0);
        receivableEntity.setOrderEntity(orderEntity);
        receivableRepository.save(receivableEntity);
    }
}
