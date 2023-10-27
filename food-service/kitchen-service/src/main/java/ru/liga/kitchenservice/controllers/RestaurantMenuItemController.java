package ru.liga.kitchenservice.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.common.util.ErrorResponse;
import ru.liga.common.util.exceptions.RestaurantMenuItemNotFoundException;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemUpdateDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemUpdatedResponse;
import ru.liga.kitchenservice.services.RestaurantMenuItemService;

import java.sql.Timestamp;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurantMenuItems")
public class RestaurantMenuItemController {

    private final RestaurantMenuItemService menuItemService;


    @GetMapping("/{id}")
    public ResponseEntity<RestaurantMenuItemDTO> getMenuItemById(@PathVariable Long id) {

        RestaurantMenuItemDTO menuItem = menuItemService.getItemById(id);

        return ResponseEntity.ok(menuItem);
    }

    @PostMapping("/{id}")
    public ResponseEntity<RestaurantMenuItemUpdatedResponse> updateRestaurantMenuItem(
            @RequestBody RestaurantMenuItemUpdateDTO restaurantMenuItemUpdateDTO, @PathVariable Long id) {

        RestaurantMenuItemUpdatedResponse response =
                menuItemService.updateItem(id, restaurantMenuItemUpdateDTO);

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
