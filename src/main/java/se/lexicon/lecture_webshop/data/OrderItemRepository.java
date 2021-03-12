package se.lexicon.lecture_webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.lecture_webshop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
