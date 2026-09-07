package com.pizzaria.core.domain.models;


import java.math.BigDecimal;
import java.util.UUID;

public class ProductsRecipes {
    private UUID product_Id;
    private UUID ingredientId;
    private BigDecimal quantityRequired;

    public ProductsRecipes(){}

    public ProductsRecipes(BigDecimal quantityRequired) {
        this.product_Id = UUID.randomUUID();
        this.ingredientId = UUID.randomUUID();
        this.quantityRequired = quantityRequired;
    }

    public UUID getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(UUID product_Id) {
        this.product_Id = product_Id;
    }

    public UUID getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(UUID ingredientId) {
        this.ingredientId = ingredientId;
    }

    public BigDecimal getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(BigDecimal quantityRequired) {
        this.quantityRequired = quantityRequired;
    }
}
