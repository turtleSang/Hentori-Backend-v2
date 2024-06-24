package com.thanksang.HentoriManager.services;

import com.thanksang.HentoriManager.config.Constance;
import com.thanksang.HentoriManager.config.EnumType.OrderStatus;
import com.thanksang.HentoriManager.config.EnumType.TypeItem;
import com.thanksang.HentoriManager.dto.*;
import com.thanksang.HentoriManager.entity.*;
import com.thanksang.HentoriManager.error.OrderErrors;
import com.thanksang.HentoriManager.payload.ItemRequest;
import com.thanksang.HentoriManager.payload.OrderRequest;
import com.thanksang.HentoriManager.payload.UpdateOrderRequest;
import com.thanksang.HentoriManager.repository.*;
import com.thanksang.HentoriManager.services.Imp.OrderServiceImp;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.LinkedList;
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
            OrderStatus status = Stream.of(OrderStatus.values())
                    .filter(orderStatusEnum -> orderStatusEnum.getCode().equals(orderRequest.getStatus()))
                    .findFirst()
                    .orElseThrow(() -> new OrderErrors("Status not exits"));
//            Create create At
            LocalDate createAt = LocalDate.now();
//            Total items
            int total = totalItem(orderRequest.getItemRequestList());
//            Save order
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setAppointment(orderRequest.getAppointment());
            orderEntity.setClientEntity(clientEntity.get());
            orderEntity.setCreateAt(createAt);
            orderEntity.setOrderStatusEnum(status);
            orderEntity.setUserEntity(userEntity.get());
            orderEntity.setTotal(total);
            orderEntity = orderRepository.save(orderEntity);
            saveListItem(orderRequest.getItemRequestList(), orderEntity);
            saveReceivable(orderEntity);
        } catch (Exception e) {
            throw new OrderErrors(e.getMessage(), e.getCause());
        }

    }

    @Override
    public void deleteOrder(String id) {
        Optional<ReceivableEntity> receivableEntity = receivableRepository.findById(id);
        if (receivableEntity.get().getRevenueEntity().size() > 0) {
            throw new OrderErrors("Order can't not delete because customer is payment");
        }
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if (!orderEntityOptional.isPresent()) {
            throw new OrderErrors("can't not find order");
        }
        OrderEntity orderEntity = orderEntityOptional.get();
        try {
            List<ItemEntity> itemEntities = orderEntity.getItemEntities();
            itemRepository.deleteAll(itemEntities);
            receivableRepository.delete(receivableEntity.get());
            orderRepository.delete(orderEntity);
        } catch (Exception e) {
            throw new OrderErrors(e.getMessage(), e.getCause());
        }

    }

    @Override
    public int findPageAll() {
        Pageable pageable = PageRequest.of(0, Constance.PageSize);
        Page<OrderEntity> orderEntities = orderRepository.findAll(pageable);
        return orderEntities.getTotalPages();
    }

    @Override
    public List<OrderDto> findAllOrder(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, Constance.PageSize, Sort.by("createAt").descending());
        Page<OrderEntity> orderEntityList = orderRepository.findAllOrders(pageable);
        List<OrderDto> orderDtoList = transferOrderDtoList(orderEntityList);
        return orderDtoList;
    }

    @Override
    public int findPageReceivable(boolean status) {
        Pageable pageable = PageRequest.of(0, Constance.PageSize, Sort.by("createAt").ascending());
        Page<OrderEntity> orderEntities = orderRepository.findAllReceivable(status, pageable);
        return orderEntities.getTotalPages();
    }

    @Override
    public List<OrderDto> findAllReceivableOrder(boolean status, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, Constance.PageSize, Sort.by("createAt").ascending());
        Page<OrderEntity> orderEntities = orderRepository.findAllReceivable(status, pageable);
        List<OrderDto> orderDtoList = transferOrderDtoList(orderEntities);
        return orderDtoList;
    }

    @Override
    public int findPageOverDue() {
        LocalDate localDate = LocalDate.now();
        OrderStatus orderStatusEnum = OrderStatus.FINISH;
        Pageable pageable = PageRequest.of(0, Constance.PageSize, Sort.by("appointment").ascending());
        Page<OrderEntity> orderEntities = orderRepository.findAllOverDue(localDate, pageable, orderStatusEnum);
        return orderEntities.getTotalPages();
    }

    @Override
    public List<OrderDto> findAllOverDueOrder(int pageNumber) {
        LocalDate localDate = LocalDate.now();
        OrderStatus orderStatusEnum = OrderStatus.FINISH;
        Pageable pageable = PageRequest.of(pageNumber, Constance.PageSize, Sort.by("appointment").ascending());
        Page<OrderEntity> orderEntities = orderRepository.findAllOverDue(localDate, pageable, orderStatusEnum);
        List<OrderDto> orderDtoList = transferOrderDtoList(orderEntities);
        return orderDtoList;
    }

    @Override
    public int findPageStatus(String codeStatus) {
        try {
            OrderStatus orderStatusEnum = findStatus(codeStatus);
            Pageable pageable = PageRequest.of(0, Constance.PageSize, Sort.by("createAt").descending());
            Page<OrderEntity> orderEntities = orderRepository.findAllByOrderStatusEnum(orderStatusEnum, pageable);
            return orderEntities.getTotalPages();
        } catch (Exception e) {
            throw new OrderErrors(e.getMessage());
        }
    }

    @Override
    public List<OrderDto> findAllWithStatus(String codeStatus, int pageNumber) {
        try {
            OrderStatus orderStatusEnum = findStatus(codeStatus);
            Pageable pageable = PageRequest.of(pageNumber, Constance.PageSize, Sort.by("createAt").descending());
            Page<OrderEntity> orderEntities = orderRepository.findAllByOrderStatusEnum(orderStatusEnum, pageable);
            List<OrderDto> orderDtoList = transferOrderDtoList(orderEntities);
            return orderDtoList;
        } catch (Exception e) {
            throw new OrderErrors(e.getMessage());
        }
    }

    @Override
    public OrderDetailDto findOrderDetailByID(String id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (!orderEntity.isPresent()) {
            return null;
        }
        OrderDetailDto orderDetailDto = transferOrderDetailDto(orderEntity.get());
        return orderDetailDto;
    }

    @Override
    public void updateOrder(String id, UpdateOrderRequest updateOrderRequest) {
        if (updateOrderRequest.getAppointment() == null){
            throw new OrderErrors("Appointment not be null");
        }
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if (!orderEntityOptional.isPresent()){
            throw new OrderErrors("Order not found");
        }
        try {
            orderEntityOptional.get().setAppointment(updateOrderRequest.getAppointment());
            OrderStatus orderStatus = Stream.of(OrderStatus.values())
                    .filter(orderStatus1 -> orderStatus1.getCode().equals(updateOrderRequest.getStatus()))
                    .findFirst().orElseThrow(() -> new OrderErrors("not found status"));
            orderEntityOptional.get().setOrderStatusEnum(orderStatus);
            orderRepository.save(orderEntityOptional.get());
        }catch (Exception e){
            throw new OrderErrors(e.getMessage());
        }
    }


    @Transactional
    @Override
    public void updateOrderItem(ItemRequest itemRequest, int id) {
        Optional<ItemEntity> itemEntityOptional = itemRepository.findById(id);
        if (!itemEntityOptional.isPresent()){
            throw new OrderErrors("not found item");
        }
        OrderEntity orderEntity = itemEntityOptional.get().getOrderEntity();
        ReceivableEntity receivableEntity = orderEntity.getReceivableEntity();
        try {
            if (itemRequest.getAmount() == 0 || itemRequest.getPrice() == 0){
                throw new OrderErrors("price and amount must be more than 0");
            }
            ItemEntity itemEntity;
            switch (itemRequest.getClass().getSimpleName()) {
                case "ShirtRequest":
                    itemEntity = modelMapper.map(itemRequest, ShirtEntity.class);
                    break;
                case "SuitRequest":
                    itemEntity = modelMapper.map(itemRequest, SuitEntity.class);
                    break;
                case "TrousersRequest":
                    itemEntity = modelMapper.map(itemRequest, TrousersEntity.class);
                    break;
                default:
                    itemEntity = modelMapper.map(itemRequest, DifferentItemEntity.class);
                    break;
            }

            int newTotalItem = itemRequest.getAmount()*itemRequest.getPrice();
            itemEntity.setTotal(newTotalItem);
            itemEntity.setOrderEntity(orderEntity);
//            Update Order
            int oldTotalItem = itemEntityOptional.get().getTotal();
            int oldTotalOrder = orderEntity.getTotal();
            int newTotalOrder = oldTotalOrder - oldTotalItem + newTotalItem;
            orderEntity.setTotal(newTotalOrder);
//            Update Receivable
            int newRemaining = newTotalOrder - receivableEntity.getPayment();
            receivableEntity.setRemaining(newRemaining);
//             save change
            itemRepository.delete(itemEntityOptional.get());
            orderRepository.save(orderEntity);
            receivableRepository.save(receivableEntity);
            itemRepository.save(itemEntity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new OrderErrors(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteItem(int id) {
        Optional<ItemEntity> itemEntity = itemRepository.findById(id);
        if (!itemEntity.isPresent()){
            throw new OrderErrors("Item not found");
        }
        OrderEntity orderEntity = itemEntity.get().getOrderEntity();
        ReceivableEntity receivableEntity = orderEntity.getReceivableEntity();
        int totalItem = itemEntity.get().getTotal();
        int totalOrder = orderEntity.getTotal() - totalItem;
        int remaining = receivableEntity.getRemaining() - totalItem;
        if (remaining <= 0 || totalOrder <= 0){
            throw new OrderErrors("Item in order can't be null");
        }
        try {
            receivableEntity.setRemaining(remaining);
            orderEntity.setTotal(totalOrder);
            receivableRepository.save(receivableEntity);
            orderRepository.save(orderEntity);
            itemRepository.delete(itemEntity.get());
        }catch (Exception e){
            throw new OrderErrors(e.getMessage());
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
                if (itemRequest.getAmount() == 0 || itemRequest.getPrice() == 0){
                    throw new OrderErrors("price and amount must be more than 0");
                }
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

    private void saveReceivable(OrderEntity orderEntity) {
        ReceivableEntity receivableEntity = new ReceivableEntity();
        receivableEntity.setClientEntity(orderEntity.getClientEntity());
        receivableEntity.setStatus(false);
        receivableEntity.setRemaining(orderEntity.getTotal());
        receivableEntity.setPayment(0);
        receivableEntity.setOrderEntity(orderEntity);
        receivableRepository.save(receivableEntity);
    }

    private List<OrderDto> transferOrderDtoList(Iterable<OrderEntity> orderEntityList) {
        List<OrderDto> orderDtoList = new LinkedList<>();
        for (OrderEntity orderEntity : orderEntityList
        ) {
            OrderDto orderDto = modelMapper.map(orderEntity, OrderDto.class);
            orderDto.setPayment(orderEntity.getReceivableEntity().getPayment());
            orderDto.setRemaining(orderEntity.getReceivableEntity().getRemaining());
            orderDto.setClientName(orderEntity.getClientEntity().getName());
            orderDto.setUsername(orderEntity.getUserEntity().getUsername());
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    private OrderStatus findStatus(String code) {
        return Stream.of(OrderStatus.values()).filter(status -> status.getCode().equals(code)).findFirst()
                .orElseThrow(() -> new OrderErrors("not found status"));
    }

    private OrderDetailDto transferOrderDetailDto(OrderEntity orderEntity) {
        OrderDetailDto orderDetailDto = modelMapper.map(orderEntity, OrderDetailDto.class);
        orderDetailDto.setPayment(orderEntity.getReceivableEntity().getPayment());
        orderDetailDto.setClientName(orderEntity.getClientEntity().getName());
        orderDetailDto.setUsername(orderEntity.getUserEntity().getUsername());
        orderDetailDto.setRemaining(orderEntity.getReceivableEntity().getRemaining());

        List<ItemDto> orderDtoList = new LinkedList<>();
        for (ItemEntity itemEntity : orderEntity.getItemEntities()
        ) {
            ItemDto itemDto;
            switch (itemEntity.getClass().getSimpleName()) {
                case "DifferentItemEntity":
                    itemDto = modelMapper.map(itemEntity, DifferentDto.class);
                    itemDto.setType(TypeItem.DIFFERENT);
                    break;
                case "ShirtEntity":
                    itemDto = modelMapper.map(itemEntity, ShirtItemDto.class);
                    itemDto.setType(TypeItem.SHIRT);
                    break;
                case "SuitEntity":
                    itemDto = modelMapper.map(itemEntity, SuitDto.class);
                    itemDto.setType(TypeItem.SUIT);
                    break;
                default:
                    itemDto = modelMapper.map(itemEntity, TrousersDto.class);
                    itemDto.setType(TypeItem.TROUSERS);
                    break;
            }
            orderDtoList.add(itemDto);
            System.out.println(itemDto.getClass().getSimpleName());
        }
        orderDetailDto.setItemDtoList(orderDtoList);
        return orderDetailDto;
    }
}
