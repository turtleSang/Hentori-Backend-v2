package com.thanksang.HentoriManager.controllers;

import com.thanksang.HentoriManager.payload.PaymentOrderRequest;
import com.thanksang.HentoriManager.services.Imp.PaymentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private PaymentServiceImp paymentServiceImp ;

    @Autowired
    public PaymentController(PaymentServiceImp paymentServiceImp) {
        this.paymentServiceImp = paymentServiceImp;
    }

    @PostMapping
    public ResponseEntity<?> paymentOrder(@RequestBody PaymentOrderRequest paymentOrderRequest){
        HttpStatus httpStatus;
        String mess;
        try {
            paymentServiceImp.paymentOrder(paymentOrderRequest);
            mess = "Payment successful";
            httpStatus = HttpStatus.OK;
        }catch (Exception paymentErrors){
            mess = paymentErrors.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(mess, httpStatus);
    }
}
