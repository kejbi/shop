package pl.biegajski.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.biegajski.shop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
