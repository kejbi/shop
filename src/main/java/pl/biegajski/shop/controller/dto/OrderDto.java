package pl.biegajski.shop.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.biegajski.shop.model.Order;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private long id;

    private String username;

    private List<OrderedItemDto> items;

    private boolean finished;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.username = order.getUser().getUsername();
        this.items = order.getItems()
                .stream()
                .map(OrderedItemDto::new)
                .collect(Collectors.toList());
        this.finished = order.isFinished();
    }
}
