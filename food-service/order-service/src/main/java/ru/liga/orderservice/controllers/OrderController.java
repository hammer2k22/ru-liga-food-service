package ru.liga.orderservice.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.orderservice.Util.Converter;
import ru.liga.orderservice.models.Order;
import ru.liga.orderservice.models.dto.OrderCreateDTO;
import ru.liga.orderservice.models.dto.OrderDTO;
import ru.liga.orderservice.models.dto.OrderToBeUpdateDTO;
import ru.liga.orderservice.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody OrderCreateDTO orderCreateDTO) {

        Order order = Converter.convertOrderCreateDTOToOrder(orderCreateDTO);

        orderService.create(order);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getAllOrders() {

        List<OrderDTO> orders = orderService.getAllOrders().stream()
                .map(Converter::convertOrderToOrderDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {

        Order order = orderService.getById(id);

        OrderDTO orderDTO = Converter.convertOrderToOrderDTO(order);

        return ResponseEntity.ok(orderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id,
                                             @RequestBody OrderToBeUpdateDTO orderToBeUpdateDTO) {

        /*Здесь я не знаю какие поля нужно обновлять. Я сделал, как будто клиент может именить
                ресторан и набор продуктов*/

        Order order = Converter.convertOrderToBeUpdateDTOToOrder(orderToBeUpdateDTO);

        orderService.update(id,order);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        orderService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
