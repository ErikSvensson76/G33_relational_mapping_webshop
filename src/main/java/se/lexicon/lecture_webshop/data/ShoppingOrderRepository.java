package se.lexicon.lecture_webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.lecture_webshop.entity.ShoppingOrder;

public interface ShoppingOrderRepository extends JpaRepository<ShoppingOrder, String> {
}
