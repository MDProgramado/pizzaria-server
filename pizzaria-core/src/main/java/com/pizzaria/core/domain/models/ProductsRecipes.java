package com.pizzaria.core.domain.models;


import java.math.BigDecimal;
import java.util.UUID;

public class ProductsRecipes {
    private UUID productId;
    private UUID ingredientId;
    private BigDecimal quantityRequired;

    public ProductsRecipes(){}

    public ProductsRecipes(UUID productId, UUID ingredientId, BigDecimal quantityRequired) {
        this.productId = productId;
        this.ingredientId = ingredientId;
        this.quantityRequired = quantityRequired;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
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
