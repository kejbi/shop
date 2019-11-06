package pl.biegajski.shop.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.biegajski.shop.model.OrderedItem;

@Getter
@Setter
@NoArgsConstructor
public class OrderedItemDto {
    private long id;

    private String item;

    private int count;

    public OrderedItemDto(OrderedItem item) {
        this.id = item.getId();
        this.item = item.getItem().getName();
        this.count = item.getCount();
    }
}
