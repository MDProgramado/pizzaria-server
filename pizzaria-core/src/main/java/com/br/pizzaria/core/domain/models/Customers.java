package com.br.pizzaria.core.domain.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Customers {
    private UUID id;
    private UUID tenant_id;
    private String name;
    private String phone;
    private String email;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;
    private OffsetDateTime deleted_at;



    public Customers(){
        //Construtor sem argumento para melhor legitibidade
    }

    public Customers(UUID id, UUID tenant_id, String name, String phone, String email, OffsetDateTime created_at, OffsetDateTime updated_at, OffsetDateTime deleted_at) {
        this.id = id;
        this.tenant_id = tenant_id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public String getUniqueKey(){
        return this.tenant_id.toString() + "_" + this.phone;
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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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
