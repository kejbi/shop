package pl.biegajski.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.biegajski.shop.model.OrderedItem;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
}
