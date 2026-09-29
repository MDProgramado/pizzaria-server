package com.br.pizzaria.core.domain.models;

import com.br.pizzaria.core.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Orders {
    private  UUID id;
    private UUID tenant_id;
    private  UUID customer_id;
    private OrderStatus orderStatus;
    private BigDecimal total_price;
    private String delivery_address_snapshot;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Orders(){}

    public Orders(UUID customer_id){
        this.id = UUID.randomUUID();
        this.customer_id = customer_id;
        this.orderStatus = OrderStatus.PENDING;
        this.created_at = LocalDateTime.now();
    }
    public UUID getId() { return id; }
    public UUID getCustomerId() { return customer_id; }
    public OrderStatus getStatus() { return orderStatus; }
    public void setStatus(OrderStatus status) { this.orderStatus = status; }


    public LocalDateTime getCreatedAt() { return created_at ; }

}
