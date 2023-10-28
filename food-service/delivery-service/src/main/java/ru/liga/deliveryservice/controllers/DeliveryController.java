package ru.liga.deliveryservice.controllers;


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
import ru.liga.common.util.exceptions.CourierNotFoundException;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.deliveryservice.models.dto.DeliveriesResponse;
import ru.liga.deliveryservice.services.DeliveryService;

import java.sql.Timestamp;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;



    @GetMapping()
    public ResponseEntity<DeliveriesResponse> getOrderByStatus(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam String status ) {

        DeliveriesResponse response = deliveryService.getDeliveriesResponseByStatus(page, size, status);
        return ResponseEntity.ok(response);

    }

    /*Курьер берет или отказывается от заказа*/
    @PostMapping("/{orderId}")
    public ResponseEntity<HttpStatus> updateOrderStatus(@PathVariable Long orderId,
                                                        @RequestBody Map<String, String> requestBody) {
        
        deliveryService.updateOrderStatus(requestBody, orderId);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    /*Курьер начинает/заканчивает смену*/
    @PostMapping("/{courierId}")
    public ResponseEntity<HttpStatus> updateCourierStatus(@PathVariable Long courierId,
                                                        @RequestBody Map<String, String> requestBody) {

        deliveryService.updateCourierStatus(requestBody, courierId);

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

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CourierNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
