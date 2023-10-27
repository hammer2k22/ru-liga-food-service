package ru.liga.kitchenservice.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.common.models.Restaurant;
import ru.liga.common.models.RestaurantMenuItem;
import ru.liga.common.repositories.RestaurantMenuItemRepository;
import ru.liga.common.repositories.RestaurantRepository;
import ru.liga.common.util.exceptions.RestaurantMenuItemNotFoundException;
import ru.liga.common.util.exceptions.RestaurantNotFoundException;
import ru.liga.kitchenservice.mappers.RestaurantMenuItemMapper;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreateDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemCreatedResponse;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemUpdateDTO;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemUpdatedResponse;
import ru.liga.kitchenservice.models.dto.RestaurantMenuItemsResponse;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantMenuItemService {

    private final RestaurantMenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMenuItemMapper menuItemMapper;

    @Transactional
    public RestaurantMenuItemCreatedResponse createNewItem(Long id,
                                                           RestaurantMenuItemCreateDTO restaurantMenuItemDTO) {

        RestaurantMenuItem restaurantMenuItem = menuItemMapper
                .restaurantMenuItemCreateDTOToRestaurantMenuItem(restaurantMenuItemDTO);

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(()->
                        new RestaurantNotFoundException("Restaurant with id = "+id+" is not found"));

        restaurantMenuItem.setRestaurant(restaurant);

        menuItemRepository.save(restaurantMenuItem);

        return new RestaurantMenuItemCreatedResponse(restaurant.getName(),restaurantMenuItem.getName());
    }

    public RestaurantMenuItemDTO getItemById(Long id) {

        RestaurantMenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(()->new RestaurantMenuItemNotFoundException
                        ("RestaurantMenuItem with id = "+id+" is not found"));

        return menuItemMapper.restaurantMenuItemToRestaurantMenuItemDTO(menuItem);
    }

    public RestaurantMenuItemsResponse getAllMenuItems(Long id, int page, int size) {

        Page<RestaurantMenuItemDTO> menuItems = menuItemRepository
                .findAllByRestaurantId(PageRequest.of(page, size), id)
                .map(menuItemMapper::restaurantMenuItemToRestaurantMenuItemDTO);

        return new RestaurantMenuItemsResponse
                (menuItems.getContent(),menuItems.getNumber(),menuItems.getSize());
    }

    @Transactional
    public RestaurantMenuItemUpdatedResponse updateItem(Long id,
                                                        RestaurantMenuItemUpdateDTO restaurantMenuItemUpdateDTO) {

        RestaurantMenuItem restaurantMenuItemUpdate = menuItemRepository.findById(id)
                .orElseThrow(()->new RestaurantMenuItemNotFoundException
                ("RestaurantMenuItem with id = "+id+" is not found"));

        restaurantMenuItemUpdate.setPrice(restaurantMenuItemUpdateDTO.getPrice());

        menuItemRepository.save(restaurantMenuItemUpdate);

        return new RestaurantMenuItemUpdatedResponse(restaurantMenuItemUpdate.getName(),
                restaurantMenuItemUpdate.getPrice());
    }

}
