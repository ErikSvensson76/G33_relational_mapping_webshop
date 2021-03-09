package se.lexicon.lecture_webshop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String orderItemId;

    private Integer amount;
    private BigDecimal itemPrice;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", table = "order_item")
    private Product product;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", table = "order_item")
    private ShoppingOrder shoppingOrder;

    public OrderItem(String orderItemId, Integer amount, BigDecimal itemPrice, Product product, ShoppingOrder shoppingOrder) {
        this.orderItemId = orderItemId;
        this.amount = amount;
        this.itemPrice = itemPrice;
        this.product = product;
        this.shoppingOrder = shoppingOrder;
    }

    public OrderItem(Integer amount, Product product, ShoppingOrder shoppingOrder) {
        this.amount = amount;
        this.product = product;
        this.shoppingOrder = shoppingOrder;
        itemPrice = product.getProductPrice()
                .multiply(BigDecimal.valueOf(amount));
    }

    public OrderItem() {
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingOrder getOrder() {
        return shoppingOrder;
    }

    public void setOrder(ShoppingOrder shoppingOrder) {
        this.shoppingOrder = shoppingOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(orderItemId, orderItem.orderItemId) && Objects.equals(amount, orderItem.amount) && Objects.equals(itemPrice, orderItem.itemPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, amount, itemPrice);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId='" + orderItemId + '\'' +
                ", amount=" + amount +
                ", itemPrice=" + itemPrice +
                '}';
    }
}
