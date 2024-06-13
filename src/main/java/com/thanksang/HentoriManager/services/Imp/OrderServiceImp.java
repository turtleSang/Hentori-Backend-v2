package com.thanksang.HentoriManager.services.Imp;

import com.thanksang.HentoriManager.payload.OrderRequest;

public interface OrderServiceImp {
    void createOrder(OrderRequest orderRequest) throws IllegalAccessException;
}
