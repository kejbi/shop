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

    private final AppUserService appUserService;

    public Order addItemsToOrder(Long itemId, int count, Long userId) {
        Order order = orderRepository.findOrderByUserIdAndFinished(userId, false).orElseThrow(EntityNotFoundException::new);
        Item item = itemRepository.findItemById(itemId).orElseThrow(EntityNotFoundException::new);
        OrderedItem addedItem = new OrderedItem();
        addedItem.setItem(item);
        addedItem.setOrder(order);
        addedItem.setCount(count);

        order.updatePrice(item.getPrice() * count);
        order.addToOrder(addedItem);

        return orderRepository.save(order);
    }

    public void removeFromOrder(Long orderedItemId, Long userId) {
        Order order = orderRepository.findOrderByUserIdAndFinished(userId, false).orElseThrow(EntityNotFoundException::new);
        OrderedItem item = orderedItemRepository.findById(orderedItemId).orElseThrow(EntityNotFoundException::new);

        order.updatePrice(item.getItem().getPrice() * item.getCount() * -1);

        orderedItemRepository.deleteById(orderedItemId);
        orderRepository.save(order);
    }

    public Order finishOrder(Long userId) throws Exception {
        Order order = orderRepository.findOrderByUserIdAndFinished(userId, false).orElseThrow(EntityNotFoundException::new);
        appUserService.debitAccount(order.getPrice(), userId);
        order.setFinished(true);
        appUserService.createNewOrder(userId);
        return orderRepository.save(order);
    }
}
