package ru.liga.kitchenservice.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.Order;
import ru.liga.common.models.OrderStatus;
import ru.liga.common.models.Restaurant;
import ru.liga.common.models.RestaurantStatus;
import ru.liga.common.repositories.OrderRepository;
import ru.liga.common.repositories.RestaurantRepository;
import ru.liga.common.util.exceptions.OrderNotFoundException;
import ru.liga.common.util.exceptions.RestaurantNotFoundException;
import ru.liga.kitchenservice.feignClients.OrderServiceClient;
import ru.liga.kitchenservice.mappers.OrderMapper;
import ru.liga.kitchenservice.models.dto.OrderDTO;
import ru.liga.kitchenservice.models.dto.OrderResponse;
import ru.liga.kitchenservice.models.dto.OrdersResponse;
import ru.liga.kitchenservice.models.dto.RestaurantResponse;

import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantResponse updateStatus(Long id, Map<String, String> status) {

        String restaurantStatus = status.get("orderAction");

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(()->new RestaurantNotFoundException("Restaurant with id "+id+" is not found"));

        restaurant.setStatus(RestaurantStatus.valueOf(restaurantStatus));

        restaurantRepository.save(restaurant);

        return new RestaurantResponse(restaurant.getName(),restaurantStatus);
    }

}
