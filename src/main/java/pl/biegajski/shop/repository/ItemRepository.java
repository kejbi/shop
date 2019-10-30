package pl.biegajski.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.biegajski.shop.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
