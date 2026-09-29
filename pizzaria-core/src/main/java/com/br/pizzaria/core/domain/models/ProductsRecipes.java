package com.br.pizzaria.core.domain.models;


import java.math.BigDecimal;
import java.util.UUID;

public class ProductsRecipes {
    private UUID product_Id;
    private UUID ingredientId;
    private BigDecimal quantityRequired;

    public ProductsRecipes(){}

    public ProductsRecipes(Products productId,Ingredients ingredientId, BigDecimal quantityRequired) {
        this.product_Id = productId.getId();
        this.ingredientId = ingredientId.getId();
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

    @Override
    public String toString() {
        return "ProductsRecipes{" +
                "\nproduct_Id=" + product_Id +
                "\n, ingredientId=" + ingredientId +
                "\n, quantityRequired=" + quantityRequired +
                '}';
    }
}
