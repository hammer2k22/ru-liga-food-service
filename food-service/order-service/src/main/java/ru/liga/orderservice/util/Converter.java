package ru.liga.orderservice.util;


import org.modelmapper.ModelMapper;
import ru.liga.orderservice.models.Order;
import ru.liga.orderservice.models.OrderItem;
import ru.liga.orderservice.models.dto.OrderCreateDTO;
import ru.liga.orderservice.models.dto.OrderDTO;
import ru.liga.orderservice.models.dto.OrderItemDTO;
import ru.liga.orderservice.models.dto.OrderItemForCreateOrderDTO;
import ru.liga.orderservice.models.dto.OrderItemForUpdateOrderDTO;
import ru.liga.orderservice.models.dto.OrderToBeUpdateDTO;
import ru.liga.orderservice.models.dto.RestrauntDTO;
import ru.liga.orderservice.models.dto.RestrauntMenuItemForOrderItemDTO;

import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    private final static ModelMapper modelMapper = new ModelMapper();


    public static Order convertOrderCreateDTOToOrder(OrderCreateDTO orderCreateDTO) {
        Order order = modelMapper.map(orderCreateDTO, Order.class);

        List<OrderItem> orderItems = orderCreateDTO.getOrderItems().stream()
                .map(Converter::convertOrderItemForCreateOrderDTOToOrderItem)
                        .collect(Collectors.toList());

        order.setId(null);
        order.setOrderItems(orderItems);

        return order;
    }

    public static OrderDTO convertOrderToOrderDTO(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        RestrauntDTO restrauntDTO = modelMapper.map(order.getRestraunt(), RestrauntDTO.class);


        List<OrderItemDTO> orderItemsDTO = order.getOrderItems().stream()
                .map(Converter::convertOrderItemToOrderItemDTO)
                .collect(Collectors.toList());


        orderDTO.setRestraunt(restrauntDTO);
        orderDTO.setItems(orderItemsDTO);

        return orderDTO;
    }

    public static Order convertOrderToBeUpdateDTOToOrder(OrderToBeUpdateDTO orderToBeUpdateDTO) {
        Order order = modelMapper.map(orderToBeUpdateDTO, Order.class);

        List<OrderItem> orderItems = orderToBeUpdateDTO.getOrderItems().stream()
                .map(Converter::convertOrderItemForUpdateOrderDTOToOrderItem)
                .collect(Collectors.toList());

        order.setId(null);
        order.setOrderItems(orderItems);

        return order;
    }

    private static OrderItemDTO convertOrderItemToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = modelMapper.map(orderItem, OrderItemDTO.class);

        RestrauntMenuItemForOrderItemDTO menuItemForOrderItemDTO =
                modelMapper.map(orderItem.getRestrauntMenuItem(), RestrauntMenuItemForOrderItemDTO.class);

        orderItemDTO.setRestrauntMenuItem(menuItemForOrderItemDTO);

        return orderItemDTO;
    }



    private static OrderItem convertOrderItemForUpdateOrderDTOToOrderItem(OrderItemForUpdateOrderDTO
                                                                                  orderItemForUpdateOrderDTO) {
        return modelMapper.map(orderItemForUpdateOrderDTO, OrderItem.class);
    }

    private static OrderItem convertOrderItemForCreateOrderDTOToOrderItem(OrderItemForCreateOrderDTO
                                                                             orderItemForCreateOrderDTO) {

        return modelMapper.map(orderItemForCreateOrderDTO, OrderItem.class);
    }



}
