package se.lexicon.lecture_webshop.dto;

import se.lexicon.lecture_webshop.misc.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

public class ShoppingOrderDTO implements Serializable {
    private String orderId;
    private LocalDateTime timeStamp;
    private BigDecimal priceTotal;
    private OrderStatus orderStatus;
    private CustomerDTO customer;
    private Collection<OrderItemDTO> orderItems;

    public ShoppingOrderDTO() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
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

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public Collection<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Collection<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
