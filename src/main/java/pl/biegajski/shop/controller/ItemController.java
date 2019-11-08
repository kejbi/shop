package pl.biegajski.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.biegajski.shop.controller.dto.ItemDto;
import pl.biegajski.shop.model.Item;
import pl.biegajski.shop.service.ItemService;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PermitAll
    @PostMapping("/add")
    public ItemDto addItem(@Valid @RequestBody ItemDto item, BindingResult bindingResult) throws ValidationException{
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Not valid data");
        }
        Item addedItem = itemService.addItem(item.getName(), item.getPrice());
        return new ItemDto(addedItem);
    }
}
