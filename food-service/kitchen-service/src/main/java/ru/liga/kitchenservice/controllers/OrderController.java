package ru.liga.kitchenservice.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.kitchenservice.models.dto.OrderDTO;
import ru.liga.kitchenservice.models.dto.OrderResponse;
import ru.liga.kitchenservice.models.dto.OrdersResponse;
import ru.liga.kitchenservice.services.OrderService;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<OrdersResponse> getOrderByStatus(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam String status) {

        OrdersResponse response = orderService.getOrdersResponseByStatus(page, size, status);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) throws JsonProcessingException {

        OrderDTO order = orderService.getOrderById(id);

        return ResponseEntity.ok(order);

    }

    /*Кухня принимает/отклоняет заказ. Обновляет статус заказа*/
    @PostMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id,
                                                           @RequestBody Map<String, String> orderStatus) {

        OrderResponse response = orderService.updateOrderStatus(id, orderStatus);

        return ResponseEntity.ok(response);

    }

}
