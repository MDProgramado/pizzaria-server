package com.pizzaria.core.domain.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Tenants {
    private UUID id;
    private  String name;
    private String suddomain;
    private boolean is_active;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    public Tenants(){

    }
    public Tenants(UUID id, String name, String suddomain, boolean is_active, OffsetDateTime created_at, OffsetDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.suddomain = suddomain;
        this.is_active = is_active;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuddomain() {
        return this.suddomain;
    }

    public void setSuddomain(String suddomain) {
        this.suddomain = suddomain;
    }

    public boolean isIs_active() {
        return this.is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
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
}
