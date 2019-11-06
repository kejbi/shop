package pl.biegajski.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.biegajski.shop.model.Item;
import pl.biegajski.shop.model.Order;
import pl.biegajski.shop.model.OrderedItem;
import pl.biegajski.shop.repository.ItemRepository;
import pl.biegajski.shop.repository.OrderRepository;
import pl.biegajski.shop.repository.OrderedItemRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final OrderedItemRepository orderedItemRepository;

    public void addItemsToOrder(Long itemId, int count, Long userId) {
        Order order = orderRepository.findOrderByUserIdAndFinished(userId, false).orElseThrow(EntityNotFoundException::new);
        Item item = itemRepository.findItemById(itemId).orElseThrow(EntityNotFoundException::new);
        OrderedItem addedItem = new OrderedItem();
        addedItem.setItem(item);
        addedItem.setOrder(order);
        addedItem.setCount(count);
        orderedItemRepository.save(addedItem);
    }
}
