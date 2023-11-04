package ru.liga.kitchenservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.common.util.ErrorResponse;
import ru.liga.common.util.exceptions.RestaurantMenuItemNotFoundException;
import ru.liga.common.util.exceptions.RestaurantNotFoundException;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreateDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreatedResponse;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemsResponse;
import ru.liga.kitchenservice.models.dto.RestaurantResponse;
import ru.liga.kitchenservice.models.dto.RestaurantsResponse;
import ru.liga.kitchenservice.services.RestaurantMenuItemService;
import ru.liga.kitchenservice.services.RestaurantService;

import java.sql.Timestamp;
import java.util.Map;


@Tag(name = "API для взаимодействия с ресторанами")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantMenuItemService menuItemService;


    @Operation(summary = "Получить список ресторанов")
    @GetMapping()
    public ResponseEntity<RestaurantsResponse> getAllRestaurants(@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size) {

        RestaurantsResponse response = restaurantService.getAllMenuItems(page, size);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить меню по номеру ресторана")
    @GetMapping("/{id}/restaurantMenuItems")
    public ResponseEntity<RestaurantMenuItemsResponse> getAllMenuItems
            (@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size) {

        RestaurantMenuItemsResponse response = menuItemService.getAllMenuItems(id,page, size);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Добавить новую позицию в меню определенного ресторана")
    @PostMapping("/{id}/restaurantMenuItems")
    public ResponseEntity<RestaurantMenuItemCreatedResponse> createNewRestaurantMenuItem(
            @PathVariable Long id,
            @RequestBody RestaurantMenuItemCreateDTO restaurantMenuItemDTO) {

        RestaurantMenuItemCreatedResponse response =
                menuItemService.createNewItem(id,restaurantMenuItemDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Operation(summary = "Изменить статус ресторана(открыт/закрыт)")
    @PostMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurantStatus
            (@PathVariable Long id, @RequestBody String status) {

        RestaurantResponse response = restaurantService.updateStatus(id, status);

        return ResponseEntity.ok(response);

    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RestaurantMenuItemNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(RestaurantNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
