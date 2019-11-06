package pl.biegajski.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.biegajski.shop.model.Order;
import pl.biegajski.shop.security.UserPrincipal;
import pl.biegajski.shop.service.CustomUserDetailsService;
import pl.biegajski.shop.service.OrderService;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{id}/{count}")
    @ResponseBody
    public void addItemToOrder(@PathVariable long id, @PathVariable int count) {
        UserPrincipal user = CustomUserDetailsService.getCurrentUser();
        orderService.addItemsToOrder(id, count, user.getId());
    }
}
