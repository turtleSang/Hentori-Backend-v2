package com.thanksang.HentoriManager.services.Imp;

import com.thanksang.HentoriManager.dto.OrderDetailDto;
import com.thanksang.HentoriManager.dto.OrderDto;
import com.thanksang.HentoriManager.payload.ItemRequest;
import com.thanksang.HentoriManager.payload.OrderRequest;
import com.thanksang.HentoriManager.payload.UpdateOrderRequest;

import java.util.List;

public interface OrderServiceImp {
    void createOrder(OrderRequest orderRequest) throws IllegalAccessException;
    void deleteOrder(String id);
    int findPageAll();
    List<OrderDto> findAllOrder(int pageNumber);
    int findPageReceivable(boolean status);
    List<OrderDto> findAllReceivableOrder(boolean status ,int pageNumber);
    int findPageOverDue();
    List<OrderDto> findAllOverDueOrder(int pageNumber);
    int findPageStatus(String codeStatus);
    List<OrderDto> findAllWithStatus(String codeStatus, int pageNumber);
    OrderDetailDto findOrderDetailByID(String id);
    void updateOrder(String id, UpdateOrderRequest updateOrderRequest);
    void updateOrderItem(ItemRequest itemRequest, int id);
    void deleteItem(int id);
}
