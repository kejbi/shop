package pl.biegajski.shop.controller.dto;

import lombok.Getter;
import lombok.Setter;
import pl.biegajski.shop.model.Item;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemDto {

    private long id;

    @NotNull
    private String name;

    @Min(value = 0)
    private float price;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
    }
}
