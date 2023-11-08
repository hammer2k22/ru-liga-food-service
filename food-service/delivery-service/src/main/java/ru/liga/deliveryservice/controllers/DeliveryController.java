package ru.liga.deliveryservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.common.models.enums.OrderStatus;
import ru.liga.common.util.ErrorResponse;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.deliveryservice.dto.DeliveriesResponse;
import ru.liga.deliveryservice.services.DeliveryService;

import java.sql.Timestamp;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Получение списка доступных заказов")
    @GetMapping()
    public ResponseEntity<DeliveriesResponse> getAvailableOrders(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {

        DeliveriesResponse response = deliveryService.getDeliveriesResponse(page, size);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Взятие заказа курьером")
    @PostMapping("/{orderId}/take")
    public ResponseEntity<HttpStatus> acceptOrder(@PathVariable UUID orderId) {

        deliveryService.updateOrderStatus(orderId, OrderStatus.DELIVERY_ACCEPTED);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @Operation(summary = "Завершение доставки курьером")
    @PostMapping("/{orderId}/complete")
    public ResponseEntity<HttpStatus> completeOrder(@PathVariable UUID orderId) {

        deliveryService.updateOrderStatus(orderId, OrderStatus.DELIVERY_COMPLETE);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(OrderNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
//
//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(CourierNotFoundException e) {
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                new Timestamp(System.currentTimeMillis())
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(OrderStatusNotFoundException e) {
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                new Timestamp(System.currentTimeMillis())
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler
//    private ResponseEntity<ErrorResponse> handleException(CourierStatusNotFoundException e) {
//        ErrorResponse response = new ErrorResponse(
//                e.getMessage(),
//                new Timestamp(System.currentTimeMillis())
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

}
