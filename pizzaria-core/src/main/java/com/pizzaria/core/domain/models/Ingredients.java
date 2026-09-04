package com.pizzaria.core.domain.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Ingredients {
    private UUID id;
    private UUID tenant_id;
    private String name;
    private String unit_measure;
    private BigDecimal current_balance;
    private BigDecimal min_threshold;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;
    private OffsetDateTime deleted_at;

    public Ingredients(){}

    public Ingredients(UUID id, UUID tenant_id, String name, String unit_measure, BigDecimal current_balance, BigDecimal min_threshold, OffsetDateTime created_at, OffsetDateTime updated_at, OffsetDateTime deleted_at) {

        this.id = id;
        this.tenant_id = tenant_id;
        this.name = name;
        this.unit_measure = unit_measure;
        this.current_balance = current_balance;
        this.min_threshold = min_threshold;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTenant_id() {
        return this.tenant_id;
    }

    public void setTenant_id(UUID tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit_measure() {
        return this.unit_measure;
    }

    public void setUnit_measure(String unit_measure) {
        this.unit_measure = unit_measure;
    }

    public BigDecimal getCurrent_balance() {
        return this.current_balance;
    }

    public void setCurrent_balance(BigDecimal current_balance) {
        this.current_balance = current_balance;
    }

    public BigDecimal getMin_threshold() {
        return this.min_threshold;
    }

    public void setMin_threshold(BigDecimal min_threshold) {
        this.min_threshold = min_threshold;
    }

    public OffsetDateTime getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(OffsetDateTime created_at) {
        this.created_at = created_at;
    }

    public OffsetDateTime getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(OffsetDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public OffsetDateTime getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(OffsetDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }
}
