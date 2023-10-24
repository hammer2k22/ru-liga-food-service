package ru.liga.kitchenservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.RestaurantMenuItem;
import ru.liga.common.repositories.RestaurantMenuItemRepository;
import ru.liga.common.util.exceptions.RestaurantMenuItemNotFoundException;
import ru.liga.kitchenservice.mappers.RestaurantMenuItemMapper;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreateDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreatedResponse;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemUpdateDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemUpdatedResponse;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantMenuItemService {

    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    private final RestaurantMenuItemMapper restaurantMenuItemMapper;

    @Transactional
    public RestaurantMenuItemCreatedResponse createNewItem(RestaurantMenuItemCreateDTO restaurantMenuItemDTO) {

        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemMapper
                .restaurantMenuItemCreateDTOToRestaurantMenuItem(restaurantMenuItemDTO);

        restaurantMenuItemRepository.save(restaurantMenuItem);

        return new RestaurantMenuItemCreatedResponse(restaurantMenuItem.getId());
    }

    @Transactional
    public RestaurantMenuItemUpdatedResponse updateItem(Long id,
                                                        RestaurantMenuItemUpdateDTO restaurantMenuItemUpdateDTO) {

        RestaurantMenuItem restaurantMenuItemUpdate = restaurantMenuItemRepository.findById(id)
                .orElse(null);
        checkIfRestaurantMenuItemIsNull(restaurantMenuItemUpdate, id);

        restaurantMenuItemUpdate.setPrice(restaurantMenuItemUpdateDTO.getPrice());

        restaurantMenuItemRepository.save(restaurantMenuItemUpdate);

        return new RestaurantMenuItemUpdatedResponse(restaurantMenuItemUpdate.getId(),
                restaurantMenuItemUpdate.getPrice());
    }

    private void checkIfRestaurantMenuItemIsNull(RestaurantMenuItem menuItem, Long menuItemId) {
        if (menuItem == null) {
            throw new RestaurantMenuItemNotFoundException
                    ("RestaurantMenuItem with id = "+menuItemId+" is not found");
        }
    }
}
