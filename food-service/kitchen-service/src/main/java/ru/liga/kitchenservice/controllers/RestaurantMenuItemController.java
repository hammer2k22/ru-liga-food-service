package ru.liga.kitchenservice.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreateDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreatedResponse;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemUpdateDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemUpdatedResponse;
import ru.liga.kitchenservice.services.RestaurantMenuItemService;
import ru.liga.kitchenservice.util.ErrorResponse;
import ru.liga.kitchenservice.util.exceptions.RestaurantMenuItemNotFoundException;

import java.sql.Timestamp;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurantMenuItems")
public class RestaurantMenuItemController {

    private final RestaurantMenuItemService restaurantMenuItemService;


    @PostMapping()
    public ResponseEntity<RestaurantMenuItemCreatedResponse> createNewRestaurantMenuItem(
            @RequestBody RestaurantMenuItemCreateDTO restaurantMenuItemDTO) {

       RestaurantMenuItemCreatedResponse response =
               restaurantMenuItemService.createNewItem(restaurantMenuItemDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<RestaurantMenuItemUpdatedResponse> updateRestaurantMenuItem(
            @RequestBody RestaurantMenuItemUpdateDTO restaurantMenuItemUpdateDTO, @PathVariable Long id) {

        RestaurantMenuItemUpdatedResponse response =
                restaurantMenuItemService.updateItem(id,restaurantMenuItemUpdateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RestaurantMenuItemNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
