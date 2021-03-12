package se.lexicon.lecture_webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.lecture_webshop.entity.OrderItem;

import java.util.Collection;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    @Query("SELECT i FROM OrderItem i WHERE i.product.productId = :id")
    Collection<OrderItem> findByProduct(@Param("id") String productId);

    @Query("SELECT i FROM OrderItem i WHERE i.shoppingOrder.orderId IN :cartIds")
    Collection<OrderItem> findByShoppingCartsIds(@Param("cartIds") Collection<String> cartIds);

}
