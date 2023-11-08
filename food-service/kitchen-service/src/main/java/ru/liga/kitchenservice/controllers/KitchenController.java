package ru.liga.kitchenservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.common.models.enums.OrderStatus;
import ru.liga.common.util.ErrorResponse;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.kitchenservice.dto.KitchenResponse;
import ru.liga.kitchenservice.dto.OrderDTO;
import ru.liga.kitchenservice.services.KitchenService;

import java.sql.Timestamp;
import java.util.UUID;


@Tag(name = "API для взаимодействия с ресторанами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kitchen")
public class KitchenController {

    private final KitchenService kitchenService;

    @Operation(summary = "Принять заказ")
    @PostMapping("/{orderId}/accept")
    public ResponseEntity<KitchenResponse> acceptOrder(@PathVariable UUID orderId) {

        KitchenResponse response = kitchenService.updateOrderStatus(orderId, OrderStatus.KITCHEN_ACCEPTED);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Отклонить заказ")
    @PostMapping("/{orderId}/decline")
    public ResponseEntity<KitchenResponse> declineOrder(@PathVariable UUID orderId) {

        KitchenResponse response = kitchenService.updateOrderStatus(orderId, OrderStatus.KITCHEN_DECLINED);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Заказ готов")
    @PostMapping("/{orderId}/ready")
    public ResponseEntity<KitchenResponse> readyOrder(@PathVariable UUID orderId) {

        KitchenResponse response = kitchenService.updateOrderStatus(orderId, OrderStatus.KITCHEN_READIED);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Получить заказ по id")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID orderId) {

        OrderDTO order = kitchenService.getOrderById(orderId);

        return ResponseEntity.ok(order);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(OrderNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
