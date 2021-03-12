package se.lexicon.lecture_webshop.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.lecture_webshop.entity.ShoppingOrder;

import java.util.Collection;

public interface ShoppingOrderRepository extends JpaRepository<ShoppingOrder, String> {

    @Query("SELECT o FROM ShoppingOrder o WHERE o.customer.customerId = :id")
    Collection<ShoppingOrder> findByCustomerId(@Param("id") String customerId);

    @Query("SELECT o FROM ShoppingOrder o JOIN FETCH o.orderItems AS orderitems WHERE orderitems.product.productId = :id")
    Collection<ShoppingOrder> findByProductId(@Param("id") String productId);
    Collection<ShoppingOrder> findByOrderItemsProductProductId(String productId);

}
