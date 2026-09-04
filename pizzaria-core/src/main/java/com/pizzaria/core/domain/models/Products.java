package com.pizzaria.core.domain.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Products {
    private UUID id;
    private UUID tenant_id;
    private String name;
    private BigDecimal base_price;
    private boolean is_active;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;
    private OffsetDateTime deleted_at;

    public Products(){}

    public Products(UUID id, UUID tenant_id, String name, BigDecimal base_price, boolean is_active, OffsetDateTime created_at, OffsetDateTime updated_at, OffsetDateTime deleted_at) {
        this.id = id;
        this.tenant_id = tenant_id;
        this.name = name;
        this.base_price = base_price;
        this.is_active = is_active;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
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

    public OffsetDateTime getCreated_at() {
        return this.created_at;
    }

    public OffsetDateTime getUpdated_at() {
        return this.updated_at;
    }

    public OffsetDateTime getDeleted_at() {
        return this.deleted_at;
    }
}
