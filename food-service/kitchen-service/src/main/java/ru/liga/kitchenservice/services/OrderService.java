package ru.liga.kitchenservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.kitchenservice.mappers.OrderMapper;
import ru.liga.kitchenservice.models.OrderStatus;
import ru.liga.kitchenservice.models.dto.OrderDTO;
import ru.liga.kitchenservice.models.dto.OrdersResponse;
import ru.liga.kitchenservice.repositories.OrderRepository;


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

        /*Поиск по каким статусам возможен? Из образца запроса(GET /orders?status=active/complete/denied)
        или из картинки, которую скинул в телеграмм-чат Андрей Лабазин, для сервиса кухни?
        Нужно ли делать проверку на правильность статуса в запросе?*/

        /*Сущность Order для данного модуля должна быть такая же, как для order-service,
         или оставить только нужные поля, а остальные удалить? Или пометить их @Trancient?
         Или оставить пока как, есть, потому что потом в д/з появится новый функционал?
         */

    }

}
