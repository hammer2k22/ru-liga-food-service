package ru.liga.orderservice.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.liga.common.models.Order;
import ru.liga.common.models.OrderStatus;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.common.util.exceptions.OrderStatusNotFoundException;
import ru.liga.orderservice.OrderServiceApplication;
import ru.liga.orderservice.models.dto.OrderResponse;
import ru.liga.orderservice.services.rabbitProducerService.RabbitProducerService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderService.class)
@ContextConfiguration(classes = {OrderServiceApplication.class})
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitProducerService rabbitProducerService;

    @InjectMocks
    private OrderService orderService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateOrderStatus_OrderFound() {

        Long orderId = 1L;
        String orderStatus = "KITCHEN_PREPARING";
        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setStatus(OrderStatus.KITCHEN_ACCEPTED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));


        OrderResponse result = orderService.updateOrderStatus(orderStatus, orderId);


        assertEquals(orderId, result.getOrderId());
        assertEquals(orderStatus, result.getOrderStatus());
        assertEquals(OrderStatus.KITCHEN_PREPARING, existingOrder.getStatus());


        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(existingOrder);
        verify(rabbitProducerService, times(1)).sendMessage(orderId + "." + orderStatus, "notification");
    }

    @Test(expected = OrderNotFoundException.class)
    public void testUpdateOrderStatus_OrderNotFound() {

        Long orderId = -1L;
        String orderStatus = "KITCHEN_PREPARING";

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        orderService.updateOrderStatus(orderStatus, orderId);

    }

    @Test(expected = OrderStatusNotFoundException.class)
    public void testUpdateOrderStatus_InvalidOrderStatus() {

        Long orderId = 1L;
        String orderStatus = "INVALID";
        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setStatus(OrderStatus.KITCHEN_PREPARING);


        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));


        orderService.updateOrderStatus(orderStatus, orderId);

    }
}