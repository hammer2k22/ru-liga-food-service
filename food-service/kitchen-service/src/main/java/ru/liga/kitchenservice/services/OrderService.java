package ru.liga.kitchenservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.OrderStatus;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.kitchenservice.mappers.OrderMapper;
import ru.liga.kitchenservice.models.dto.OrderDTO;
import ru.liga.kitchenservice.models.dto.OrdersResponse;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    public OrdersResponse getOrdersResponseByStatus(int page, int size,String status) {

        Page<OrderDTO> orders = orderRepository
                .findAllByStatus(PageRequest.of(page, size), OrderStatus.valueOf(status))
                .map(orderMapper::orderToOrderDTO);

        return new OrdersResponse(orders.getContent(),orders.getNumber(),orders.getSize());

        /*Нужно ли делать проверку на правильность статуса в запросе?*/

    }

}
