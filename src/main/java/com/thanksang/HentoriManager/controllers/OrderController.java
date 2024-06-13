package com.thanksang.HentoriManager.controllers;

import com.thanksang.HentoriManager.error.OrderErrors;
import com.thanksang.HentoriManager.payload.OrderRequest;
import com.thanksang.HentoriManager.services.Imp.OrderServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private OrderServiceImp orderServiceImp;

    public OrderController(OrderServiceImp orderServiceImp) {
        this.orderServiceImp = orderServiceImp;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrderRequest orderRequest){
        HttpStatus httpStatus;
        String mess;
        try {
            orderServiceImp.createOrder(orderRequest);
            mess = "Created orders";
            httpStatus = HttpStatus.OK;
        } catch (OrderErrors | IllegalAccessException orderErrors){
            mess = orderErrors.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(mess, httpStatus);

    }
}
