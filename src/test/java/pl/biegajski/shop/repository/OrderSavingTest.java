package pl.biegajski.shop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.biegajski.shop.model.AppUser;
import pl.biegajski.shop.model.Item;
import pl.biegajski.shop.model.Order;
import pl.biegajski.shop.model.OrderedItem;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
/*
    Each test must be run individual, because of database init
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderSavingTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderedItemRepository orderedItemRepository;

    @BeforeEach
    void setUp() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("passwd1");
        user.setRole("USER");
        appUserRepository.save(user);

        Item item = new Item();
        item.setId(2L);
        item.setName("item");
        item.setPrice(40f);
        itemRepository.save(item);

        Order order = new Order();
        order.setId(3L);
        order.setUser(user);
        orderRepository.save(order);
    }


    @Test
    void whenItemFindById_thenItIsReturned() {
        assertThat(itemRepository.findItemById(2L).isPresent(), is(true));
    }

    @Test
    void whenFindOrderByUserIdAndFinished_thenOrderIsReturned() {
        assertThat(orderRepository.findOrderByUserIdAndFinished(1L, false).isPresent(), is(true));
    }

    @Test
    void whenOrderWithAddedItemIsSaved_thenOrderedItemsAreSaved() {
        Order order = orderRepository.findById(3L).get();
        Item item = itemRepository.findItemById(2L).get();
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setCount(2);
        orderedItem.setItem(item);
        orderedItem.setOrder(order);

        order.addToOrder(orderedItem);

        orderRepository.save(order);

        assertThat(orderedItemRepository.findAll().size(), is(1));
    }
}
