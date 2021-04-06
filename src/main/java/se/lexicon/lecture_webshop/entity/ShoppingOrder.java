package se.lexicon.lecture_webshop.entity;

import org.hibernate.annotations.GenericGenerator;
import se.lexicon.lecture_webshop.misc.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class ShoppingOrder {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String orderId;
    private LocalDateTime timeStamp;
    private BigDecimal priceTotal;
    private OrderStatus orderStatus;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", table = "shopping_order")
    private Customer customer;

    @OneToMany(
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "shoppingOrder",
            orphanRemoval = true
    )
    private Collection<OrderItem> orderItems;

    public ShoppingOrder(String orderId, LocalDateTime timeStamp, BigDecimal priceTotal, OrderStatus orderStatus, Customer customer, Collection<OrderItem> orderItems) {
        this.orderId = orderId;
        this.timeStamp = timeStamp;
        this.priceTotal = priceTotal;
        this.orderStatus = orderStatus;
        this.customer = customer;
        setOrderItems(orderItems);
    }

    public ShoppingOrder(OrderStatus orderStatus, Customer customer, Collection<OrderItem> orderItems) {
        this(null, null, null, orderStatus, customer, orderItems);
    }

    public ShoppingOrder(OrderStatus orderStatus, Customer customer) {
        this(orderStatus, customer, null);
    }

    public ShoppingOrder() {
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public BigDecimal getPriceTotal() {
        if(priceTotal == null){
            BigDecimal bigDecimal = BigDecimal.ZERO;
            for(OrderItem item : orderItems){
                bigDecimal = bigDecimal.add(item.getItemPrice());
            }
            return bigDecimal.setScale(2, RoundingMode.HALF_UP);
        }
        return priceTotal.setScale(2, RoundingMode.HALF_UP);
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Collection<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItem> orderItems) {
        if(orderItems == null) orderItems = new ArrayList<>();
        if(orderItems.isEmpty()){
            if(this.orderItems != null){
                for(OrderItem item : this.orderItems){
                    item.setOrder(null);
                }
            }
        }else{
            for(OrderItem orderItem : orderItems){
                orderItem.setOrder(this);
            }
        }
        this.orderItems = orderItems;
    }

    public void addOrderItems(OrderItem...items){
        if(orderItems == null) orderItems = new ArrayList<>();
        if(items.length > 0){
            for(OrderItem item : items){
                if(!orderItems.contains(item)){
                    orderItems.add(item);
                    item.setOrder(this);
                }
            }
        }
    }

    public void removeOrderItems(OrderItem...items){
        if(orderItems == null) orderItems = new ArrayList<>();
        if(items.length > 0){
            for(OrderItem item : items){
                if(orderItems.contains(item)){
                    orderItems.remove(item);
                    item.setOrder(null);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingOrder shoppingOrder = (ShoppingOrder) o;
        return Objects.equals(orderId, shoppingOrder.orderId) && Objects.equals(timeStamp, shoppingOrder.timeStamp) && Objects.equals(priceTotal, shoppingOrder.priceTotal) && orderStatus == shoppingOrder.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, timeStamp, priceTotal, orderStatus);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", timeStamp=" + timeStamp +
                ", priceTotal=" + priceTotal +
                ", orderStatus=" + orderStatus +
                '}';
    }

    @PrePersist
    private void prePersist(){
        if(this.orderItems != null){
            if(this.priceTotal == null) this.priceTotal = BigDecimal.ZERO;
            for(OrderItem item : this.orderItems){
                this.priceTotal = this.priceTotal.add(item.getItemPrice());
            }
        }
        timeStamp = LocalDateTime.now();
    }
}
