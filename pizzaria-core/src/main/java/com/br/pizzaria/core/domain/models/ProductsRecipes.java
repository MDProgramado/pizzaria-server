package com.br.pizzaria.core.domain.models;


import java.math.BigDecimal;
import java.util.UUID;

public class ProductsRecipes {
    private UUID productId;
    private UUID ingredientId;
    private UUID tenantId;
    private BigDecimal quantityRequired;

    public ProductsRecipes(){}

    public ProductsRecipes(UUID productId, UUID ingredientId, BigDecimal quantityRequired, UUID tenantId) {
        this.productId = productId;
        this.ingredientId = ingredientId;
        this.quantityRequired = quantityRequired;
        this.tenantId = tenantId;
    }


    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public UUID getIngredientId() { return ingredientId; }
    public void setIngredientId(UUID ingredientId) { this.ingredientId = ingredientId; }

    public UUID getTenantId() { return tenantId; }
    public void setTenantId(UUID tenantId) { this.tenantId = tenantId; }

    public BigDecimal getQuantityRequired() { return quantityRequired; }
    public void setQuantityRequired(BigDecimal quantityRequired) { this.quantityRequired = quantityRequired; }

    @Override
    public String toString() {
        return "ProductsRecipes{" +
                "productId=" + productId +
                ", ingredientId=" + ingredientId +
                ", tenantId=" + tenantId +
                ", quantityRequired=" + quantityRequired +
                '}';
    }
}
