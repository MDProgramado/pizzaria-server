package com.pizzaria.core.domain.enums;
import java.util.EnumSet;
import java.util.Set;

public enum OrderStatus {
    PENDING("Pendente"),
    PREPARING("Em Preparo"),
    SHIPPED("Enviado"),
    DELIVERED("Entregue"),
    CANCELLED("Cancelado");

    private final String description;
    private Set<OrderStatus> allowedNextStatuses;
    OrderStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    static {
        PENDING.allowedNextStatuses = EnumSet.of(PREPARING, CANCELLED);
        PREPARING.allowedNextStatuses = EnumSet.of(SHIPPED, CANCELLED);
        SHIPPED.allowedNextStatuses = EnumSet.of(DELIVERED, CANCELLED);
        DELIVERED.allowedNextStatuses = EnumSet.noneOf(OrderStatus.class);
        CANCELLED.allowedNextStatuses = EnumSet.noneOf(OrderStatus.class); 
    }
}
