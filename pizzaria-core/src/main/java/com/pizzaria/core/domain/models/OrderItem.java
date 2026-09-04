package com.pizzaria.core.domain.models;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {
    private UUID id;
    private UUID tenant_at;
    private UUID order_id;
    private UUID product;
    private Integer quantity;
    private BigDecimal unit_price_sale;

    public OrderItem(){}

    public OrderItem(UUID product_id, Integer quantity, BigDecimal unit_price_sale) {
        this.id = UUID.randomUUID();
        this.tenant_at = UUID.randomUUID();
        this.order_id = UUID.randomUUID();
        this.product = UUID.randomUUID();
        this.quantity = quantity;
        this.unit_price_sale = unit_price_sale;
    }

    public BigDecimal getSubtoal(){
        if (this.quantity == null || unit_price_sale == null) {
            return BigDecimal.ZERO;
        }

        return this.unit_price_sale.multiply(BigDecimal.valueOf(quantity));
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTenant_at() {
        return tenant_at;
    }

    public void setTenant_at(UUID tenant_at) {
        this.tenant_at = tenant_at;
    }

    public UUID getOrder_id() {
        return order_id;
    }

    public void setOrder_id(UUID order_id) {
        this.order_id = order_id;
    }

    public UUID getProduct() {
        return product;
    }

    public void setProduct(UUID product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnit_price_sale() {
        return unit_price_sale;
    }

    public void setUnit_price_sale(BigDecimal unit_price_sale) {
        this.unit_price_sale = unit_price_sale;
    }
}
