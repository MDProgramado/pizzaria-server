package com.pizzaria.core.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Products {
    private UUID id;
    private UUID tenant_id;
    private String name;
    private BigDecimal base_price;
    private boolean is_active;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;

    public Products(){}

    public Products(String name, BigDecimal base_price, boolean is_active, LocalDateTime created_at) {
        this.id = UUID.randomUUID();
        this.tenant_id = UUID.randomUUID();
        this.name = name;
        this.base_price = base_price;
        this.is_active = is_active;
        this.created_at = created_at;
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getTenant_id() {
        return this.tenant_id;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getBase_price() {
        return this.base_price;
    }

    public boolean isIs_active() {
        return this.is_active;
    }
    public void setIs_active(boolean is_active){
        this.is_active = is_active;
    }


    public LocalDateTime getCreated_at() {
        return this.created_at;
    }

    public LocalDateTime getUpdated_at() {
        return this.updated_at;
    }

    public LocalDateTime getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
