package pl.biegajski.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.model.Item;
import pl.biegajski.shop.model.Order;
import pl.biegajski.shop.repository.ItemRepository;
import pl.biegajski.shop.repository.OrderRepository;
import pl.biegajski.shop.repository.OrderedItemRepository;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest  {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OrderedItemRepository orderedItemRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private OrderService orderService;

    private Optional<Order> testOrder;

    private Optional<Item> testItem;

    @BeforeEach
    void setUp() {
        Item item = new Item();
        item.setId(1);
        item.setName("item");
        item.setPrice(20f);

        Order order = new Order();
        order.setId(2);
        order.setUser(new AppUser());

        testItem = Optional.of(item);
        testOrder = Optional.of(order);
    }



    Order addItemsToOrder(int count) {
        when(orderRepository.findOrderByUserIdAndFinished(any(Long.class), eq(false))).thenReturn(testOrder);
        when(itemRepository.findItemById(any(Long.class))).thenReturn(testItem);
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder.get());

        return orderService.addItemsToOrder(1L, count, 2L);
    }

    @Test
    void whenItemIsAddedToOrder_thenTotalPriceIsUpdatedAndItemIsAdded() {
        Order order = addItemsToOrder(2);
        assertThat(order.getPrice(), is(40f));
        assertThat(order.getItems().size(), is(1));
    }

    @Test
    void whenItemIsRemoved_thenTotalPriceIsReducedAndItemIsDeleted() {
        Order order = addItemsToOrder(2);
        when(orderedItemRepository.findById(any(Long.class))).thenReturn(Optional.of(order.getItems().get(0)));
        orderService.removeFromOrder(2L,1L);

        assertThat(order.getItems().size(), is(0));
        assertThat(order.getPrice(), is(0f));
    }

    @Test
    void whenOrderIsFinished_thenItIsFinished() throws Exception {
        when(orderRepository.findOrderByUserIdAndFinished(any(Long.class), eq(false))).thenReturn(testOrder);
        when(appUserService.debitAccount(any(Float.class), any(Long.class))).thenReturn(1f);
        when(appUserService.createNewOrder(any(Long.class))).thenReturn(new AppUser());
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder.get());

        Order order = orderService.finishOrder(1L);

        assertThat(order.isFinished(), is(true));


    }
}
