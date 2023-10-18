package ru.liga.orderservice.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.orderservice.models.dto.OrderCreateResponse;
import ru.liga.orderservice.models.dto.OrdersResponse;
import ru.liga.orderservice.models.dto.OrderCreateDTO;
import ru.liga.orderservice.models.dto.OrderDTO;
import ru.liga.orderservice.services.OrderService;
import ru.liga.orderservice.util.ErrorResponse;
import ru.liga.orderservice.util.exceptions.OrderNotFoundException;
import ru.liga.orderservice.util.exceptions.RestaurantMenuItemNotFoundException;
import ru.liga.orderservice.util.exceptions.RestaurantNotFoundException;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderCreateResponse> create(@RequestBody OrderCreateDTO orderCreateDTO) {

     OrderCreateResponse response = orderService.createNewOrder(orderCreateDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<OrdersResponse> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {

        OrdersResponse response = orderService.getOrdersResponse(page,size);

        return ResponseEntity.ok(response);


    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {

        OrderDTO order = orderService.getOrderDTOById(id);

        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        orderService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(OrderNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
               new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RestaurantNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RestaurantMenuItemNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
