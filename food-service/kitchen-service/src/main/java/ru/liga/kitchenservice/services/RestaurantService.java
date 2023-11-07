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
import ru.liga.common.util.exceptions.RestaurantStatusNotFoundException;
import ru.liga.kitchenservice.mappers.RestaurantMapper;
import ru.liga.kitchenservice.models.dto.RestaurantDTO;
import ru.liga.kitchenservice.models.dto.RestaurantResponse;
import ru.liga.kitchenservice.models.dto.RestaurantsResponse;

import java.util.Arrays;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;


    @Transactional
    public RestaurantResponse updateStatus(Long id, String restaurantStatus) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + id + " is not found"));

        boolean wrongFormatRestaurantStatus = Arrays.stream(RestaurantStatus.values())
                .map(Enum::toString)
                .noneMatch(status->status.equals(restaurantStatus));

        if(wrongFormatRestaurantStatus){
            throw new RestaurantStatusNotFoundException("Status " + restaurantStatus + " is not found");
        }

        restaurant.setStatus(RestaurantStatus.valueOf(restaurantStatus));

        restaurantRepository.save(restaurant);

        return new RestaurantResponse(restaurant.getName(), restaurantStatus);
    }

    public RestaurantsResponse getAllRestaurants(int page, int size) {
        Page<RestaurantDTO> restaurants = restaurantRepository
                .findAll(PageRequest.of(page, size))
                .map(restaurantMapper::restaurantToRestaurantDTO);

        return new RestaurantsResponse
                (restaurants.getContent(), restaurants.getNumber(), restaurants.getSize());
    }


}
