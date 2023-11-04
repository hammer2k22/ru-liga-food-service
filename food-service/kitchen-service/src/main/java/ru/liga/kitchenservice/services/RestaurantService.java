package ru.liga.kitchenservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.Restaurant;
import ru.liga.common.models.RestaurantStatus;
import ru.liga.common.repositories.RestaurantRepository;
import ru.liga.common.util.exceptions.RestaurantNotFoundException;
import ru.liga.kitchenservice.mappers.RestaurantMapper;
import ru.liga.kitchenservice.models.dto.RestaurantDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemsResponse;
import ru.liga.kitchenservice.models.dto.RestaurantResponse;
import ru.liga.kitchenservice.models.dto.RestaurantsResponse;

import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Transactional
    public RestaurantResponse updateStatus(Long id, String status) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " is not found"));

        restaurant.setStatus(RestaurantStatus.valueOf(status));

        restaurantRepository.save(restaurant);

        return new RestaurantResponse(restaurant.getName(), status);
    }

    public RestaurantsResponse getAllMenuItems(int page, int size) {
        Page<RestaurantDTO> restaurants = restaurantRepository
                .findAll(PageRequest.of(page, size))
                .map(restaurantMapper::restaurantToRestaurantDTO);

        return new RestaurantsResponse
                (restaurants.getContent(), restaurants.getNumber(), restaurants.getSize());
    }
}
