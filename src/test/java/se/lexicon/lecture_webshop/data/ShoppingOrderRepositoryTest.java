package se.lexicon.lecture_webshop.data;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.lecture_webshop.entity.*;
import se.lexicon.lecture_webshop.misc.OrderStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShoppingOrderRepositoryTest {

    @Autowired private TestEntityManager testEntityManager;
    @Autowired private ShoppingOrderRepository testObject;

    List<Product> persistedProducts;
    Customer persistedCustomer;

    private ShoppingOrder persistedOrder;

    @BeforeEach
    void setUp() {
        persistedCustomer = new Customer();
        persistedCustomer.setAddress(new Address(null, "Skogen 1", "35263", "Växjö", "Sweden"));
        persistedCustomer.setEmail("nisse@gmail.com");
        persistedCustomer.setFirstName("Nils");
        persistedCustomer.setLastName("Nilsson");
        persistedCustomer.setUserDetails(testEntityManager.persist(new AppUser(null, "nisse", "1234", "customer")));

        persistedCustomer = testEntityManager.persist(persistedCustomer);

        Product p1 = new Product(null, "p1", BigDecimal.TEN, null);
        Product p2 = new Product(null, "p2", BigDecimal.valueOf(90.99), null);

        persistedProducts = Stream.of(p1, p2)
                .map(testEntityManager::persist)
                .collect(Collectors.toList());


        OrderItem i1 = new OrderItem(), i2 = new OrderItem();
        i1.setProduct(persistedProducts.get(0));
        i1.setAmount(10);
        i1.setItemPrice(persistedProducts.get(0).getProductPrice());

        i2.setProduct(persistedProducts.get(1));
        i2.setAmount(5);
        i2.setItemPrice(persistedProducts.get(1).getProductPrice());

        ShoppingOrder order = new ShoppingOrder();
        order.setCustomer(persistedCustomer);
        order.setOrderStatus(OrderStatus.RECEIVED);
        order.setOrderItems(Arrays.asList(i1, i2));

        testEntityManager.persistAndFlush(order);

        persistedOrder = order;
    }

    @Test
    @DisplayName("given id findByCustomerId return collection of 1")
    void findByCustomerId() {
        String id = persistedCustomer.getCustomerId();

        Collection<ShoppingOrder> result = testObject.findByCustomerId(id);

        System.out.println(result);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

    }

    @Test
    @DisplayName("given productId findByProductId return collection of 1")
    void findByProductId() {
        String productId = persistedProducts.get(0).getProductId();

        Collection<ShoppingOrder> result = testObject.findByProductId(productId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }


}