package ru.liga.kitchenservice.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.kitchenservice.models.dto.OrdersResponse;
import ru.liga.kitchenservice.services.OrderService;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;



    @GetMapping()
    public ResponseEntity<OrdersResponse> getOrderByStatus(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam String status ) {

        OrdersResponse response = orderService.getOrdersResponseByStatus(page, size, status);

        return ResponseEntity.ok(response);

    }

}
