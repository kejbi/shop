package pl.biegajski.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.biegajski.shop.controller.dto.OrderDto;
import pl.biegajski.shop.controller.dto.OrderedItemDto;
import pl.biegajski.shop.model.Order;
import pl.biegajski.shop.model.OrderedItem;
import pl.biegajski.shop.security.UserPrincipal;
import pl.biegajski.shop.service.CustomUserDetailsService;
import pl.biegajski.shop.service.OrderService;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @RolesAllowed({"ROLE_USER"})
    @PostMapping("/{id}/{count}")
    @ResponseBody
    public OrderDto addItemToOrder(@PathVariable long id, @PathVariable int count) {
        UserPrincipal user = CustomUserDetailsService.getCurrentUser();
        Order order = orderService.addItemsToOrder(id, count, user.getId());
        return new OrderDto(order);
    }

    @RolesAllowed({"ROLE_USER"})
    @DeleteMapping("/{id}")
    @ResponseBody
    public void removeFromOrder(@PathVariable long id) {
        UserPrincipal user = CustomUserDetailsService.getCurrentUser();
        orderService.removeFromOrder(id, user.getId());
    }

    @RolesAllowed({"ROLE_USER"})
    @PostMapping("/finish")
    @ResponseBody
    public OrderDto finishOrder() throws Exception {
        UserPrincipal user = CustomUserDetailsService.getCurrentUser();
        Order order = orderService.finishOrder(user.getId());
        return new OrderDto(order);
    }
}
