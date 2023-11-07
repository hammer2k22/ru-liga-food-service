package ru.liga.kitchenservice.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.common.util.ErrorResponse;
import ru.liga.common.util.exceptions.OrderStatusNotFoundException;
import ru.liga.kitchenservice.models.dto.OrderDTO;
import ru.liga.kitchenservice.models.dto.OrderResponse;
import ru.liga.kitchenservice.models.dto.OrdersResponse;
import ru.liga.kitchenservice.services.OrderService;

import java.sql.Timestamp;


@Tag(name = "API для взаимодействия с ресторанами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Получить список заказов в зависимости от статуса")
    @GetMapping()
    public ResponseEntity<OrdersResponse> getOrdersByStatus(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam String status) {

        OrdersResponse response = orderService.getOrdersResponseByStatus(page, size, status);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Получить заказ по номеру")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id)  {

        OrderDTO order = orderService.getOrderById(id);

        return ResponseEntity.ok(order);

    }

    @Operation(summary = "Обновить статус заказа(принять/отклонить/отправить на доставку)")
    @PostMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id,
                                                           @RequestBody String status) {

        OrderResponse response = orderService.updateOrderStatus(id, status);

        return ResponseEntity.ok(response);

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
