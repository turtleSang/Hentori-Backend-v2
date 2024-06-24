package com.thanksang.HentoriManager.controllers;

import com.thanksang.HentoriManager.dto.OrderDetailDto;
import com.thanksang.HentoriManager.dto.OrderDto;
import com.thanksang.HentoriManager.error.OrderErrors;
import com.thanksang.HentoriManager.payload.ItemRequest;
import com.thanksang.HentoriManager.payload.OrderRequest;
import com.thanksang.HentoriManager.payload.UpdateOrderRequest;
import com.thanksang.HentoriManager.services.Imp.OrderServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private OrderServiceImp orderServiceImp;

    public OrderController(OrderServiceImp orderServiceImp) {
        this.orderServiceImp = orderServiceImp;
    }

    @GetMapping("/page/all")
    public ResponseEntity<?> getPageAllOrder(){
        try {
            int page = orderServiceImp.findPageAll();
            return new ResponseEntity<>(page, HttpStatus.OK);
        }catch (Exception e){
            String mess = e.getMessage();
            return new ResponseEntity<>(mess, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrder(@RequestParam(defaultValue = "0") int pageNumber ){
        HttpStatus httpStatus = HttpStatus.OK;
        List<OrderDto> orderDtoList = orderServiceImp.findAllOrder(pageNumber);
        if (orderDtoList.isEmpty()){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(orderDtoList, httpStatus);
    }

    @GetMapping("/page/receivable")
    public ResponseEntity<?> getPageReceivableOrder(@RequestParam(defaultValue = "false") boolean status){
        try {
            int page = orderServiceImp.findPageReceivable(status);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }catch (Exception e){
            String mess = e.getMessage();
            return new ResponseEntity<>(mess, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/receivable")
    public ResponseEntity<?> getReceivableOrder(@RequestParam(defaultValue = "false") boolean status ,@RequestParam(defaultValue = "0") int pageNumber ){
        HttpStatus httpStatus = HttpStatus.OK;
        List<OrderDto> orderDtoList = orderServiceImp.findAllReceivableOrder(status, pageNumber);
        if (orderDtoList.isEmpty()){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(orderDtoList, httpStatus);
    }

    @GetMapping("/page/over")
    public ResponseEntity<?> getPageOverDue(){
        try {
            int page = orderServiceImp.findPageOverDue();
            return new ResponseEntity<>(page, HttpStatus.OK);
        }catch (Exception e){
            String mess = e.getMessage();
            return new ResponseEntity<>(mess, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/over")
    public ResponseEntity<?> getAllOverDue(@RequestParam(defaultValue = "0") int pageNumber){
        HttpStatus httpStatus = HttpStatus.OK;
        List<OrderDto> orderDtoList = orderServiceImp.findAllOverDueOrder(pageNumber);
        if (orderDtoList.isEmpty()){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(orderDtoList, httpStatus);
    }

    @GetMapping("/page/status")
    public ResponseEntity<?> getPageAllStatus(@RequestParam String codeStatus){
        try {
            int page = orderServiceImp.findPageStatus(codeStatus);
            return new ResponseEntity<>(page, HttpStatus.OK);
        }catch (Exception e){
            String mess = e.getMessage();
            return new ResponseEntity<>(mess, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getAllStatus(@RequestParam String codeStatus, @RequestParam int pageNumber){
        HttpStatus httpStatus = HttpStatus.OK;
        List<OrderDto> orderDtoList = orderServiceImp.findAllWithStatus(codeStatus, pageNumber);
        if (orderDtoList.isEmpty()){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(orderDtoList, httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailOrder(@PathVariable String id){
        OrderDetailDto orderDto = orderServiceImp.findOrderDetailByID(id);
        if (orderDto != null){
            return new ResponseEntity<>(orderDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<?> updateItem(@PathVariable int id, @RequestBody ItemRequest itemRequest){
        HttpStatus httpStatus;
        String mess;
        try {
            orderServiceImp.updateOrderItem(itemRequest, id);
            mess = "Items is updated";
            httpStatus = HttpStatus.OK;
        }catch (Exception e){
            httpStatus = HttpStatus.BAD_REQUEST;
            mess = e.getMessage();
        }
        return new ResponseEntity<>(mess, httpStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable String id, @RequestBody UpdateOrderRequest updateOrderRequest){
        HttpStatus httpStatus;
        String mess;
        try {
            orderServiceImp.updateOrder(id, updateOrderRequest);
            mess = "Order is updated";
            httpStatus = HttpStatus.OK;
        }catch (Exception e){
            httpStatus = HttpStatus.BAD_REQUEST;
            mess = e.getMessage();
        }
        return new ResponseEntity<>(mess, httpStatus);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable int id){
        HttpStatus httpStatus;
        String mess;
        try {
            orderServiceImp.deleteItem(id);
            mess = "Items is deleted";
            httpStatus = HttpStatus.OK;
        }catch (Exception e){
            httpStatus = HttpStatus.BAD_REQUEST;
            mess = e.getMessage();
        }
        return new ResponseEntity<>(mess, httpStatus);
    }

    @PostMapping
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        HttpStatus httpStatus;
        String mess;
        try {
            orderServiceImp.deleteOrder(id);
            mess = "Order has deleted";
            httpStatus = HttpStatus.OK;
        }catch (Exception e){
            mess = e.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(mess, httpStatus);

    }
}
