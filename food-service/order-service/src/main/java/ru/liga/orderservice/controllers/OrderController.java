package ru.liga.orderservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.liga.common.models.OrderStatus;
import ru.liga.common.util.ErrorResponse;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.common.util.exceptions.OrderStatusNotFoundException;
import ru.liga.common.util.exceptions.RestaurantMenuItemNotFoundException;
import ru.liga.common.util.exceptions.RestaurantNotFoundException;
import ru.liga.orderservice.models.dto.OrderCreateDTO;
import ru.liga.orderservice.models.dto.OrderCreateResponse;
import ru.liga.orderservice.models.dto.OrderDTO;
import ru.liga.orderservice.models.dto.OrderResponse;
import ru.liga.orderservice.models.dto.OrdersResponse;
import ru.liga.orderservice.services.OrderService;

import java.sql.Timestamp;
@Tag(name = "API для взаимодействия с заказами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Создать новый заказ")
    @PostMapping()
    public ResponseEntity<OrderCreateResponse> create(@RequestBody OrderCreateDTO orderCreateDTO) {

        OrderCreateResponse response = orderService.createNewOrder(orderCreateDTO);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить историю заказов постранично")
    @GetMapping()
    public ResponseEntity<OrdersResponse> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {

        OrdersResponse response = orderService.getAllOrders(page, size);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Получить заказ но номеру")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {

        OrderDTO order = orderService.getOrderById(id);

        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Оплатить заказ")
    @PostMapping("/pay/{id}")
    public ResponseEntity<OrderResponse> payOrder(@PathVariable Long id) {

        OrderResponse response = orderService.updateOrderStatus(OrderStatus.CUSTOMER_PAID.toString(), id);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Обновить статус заказа")
    @PostMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id,
                                                           @RequestBody String status) {

        OrderResponse response = orderService.updateOrderStatus(status, id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Отменить заказ")
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> delete(@PathVariable Long id) {

        OrderResponse response = orderService.delete(id);

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(OrderNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RestaurantNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RestaurantMenuItemNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(OrderStatusNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
