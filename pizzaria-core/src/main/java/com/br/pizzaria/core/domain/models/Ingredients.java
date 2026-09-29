package com.br.pizzaria.core.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ingredients implements Comparable<Ingredients>{
    private UUID id;
    private UUID tenant_id;
    private String name;
    private String unit_measure;
    private BigDecimal current_balance;
    private BigDecimal min_threshold;
    private LocalDateTime created_at;
    private LocalDateTime  updated_at;
    private LocalDateTime  deleted_at;

    public Ingredients(){}

    public Ingredients(String name, String unit_measure, BigDecimal current_balance, BigDecimal min_threshold, LocalDateTime created_at) {

        this.id = UUID.randomUUID();
        this.tenant_id = UUID.randomUUID();
        this.name = name;
        this.unit_measure = unit_measure;
        this.current_balance = current_balance;
        this.min_threshold = min_threshold;
        this.created_at = LocalDateTime.now();
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

    public LocalDateTime getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getDeleted_at() {
        return this.deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

    @Override
    public int compareTo(Ingredients outroIngrediente) {

        return this.name.compareToIgnoreCase(outroIngrediente.getName());
    }
}
