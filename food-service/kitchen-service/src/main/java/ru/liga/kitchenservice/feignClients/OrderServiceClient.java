package ru.liga.kitchenservice.feignClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "http://localhost:8080")
public interface OrderServiceClient {

    @GetMapping("/api/v1/orders/{id}")
    String getOrder(@PathVariable Long id);

    @PostMapping("/api/v1/orders/{id}")
    String updateOrder(@PathVariable Long id, @RequestBody String status);

}
