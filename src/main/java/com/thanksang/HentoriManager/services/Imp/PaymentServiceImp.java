package com.thanksang.HentoriManager.services.Imp;

import com.thanksang.HentoriManager.payload.PaymentOrderRequest;

public interface PaymentServiceImp {
    void paymentOrder(PaymentOrderRequest paymentOrderRequest) throws IllegalAccessException;
}
