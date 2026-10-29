package com.br.pizzaria.core.domain.repository;

import com.br.pizzaria.core.domain.models.Ingredients;
import com.br.pizzaria.core.domain.models.Products;
import com.br.pizzaria.core.domain.models.ProductsRecipes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankListProductsRecipes {
    private static  final List<ProductsRecipes> listaReceitaProdutos = new ArrayList<>();

    public static List<ProductsRecipes> getAllProductsRecipes(){
        return listaReceitaProdutos;
    }

    public static List<ProductsRecipes> getRecipesByProductId(UUID id){
        return listaReceitaProdutos.stream()
                .filter(i -> i.getProductId().equals(id))
                .toList();
    }

  public static boolean save(UUID productId, UUID ingredientId, BigDecimal quantityRequired, UUID tenant_id){
        ProductsRecipes productsRecipes = new ProductsRecipes(productId, ingredientId,quantityRequired, tenant_id);
        return listaReceitaProdutos.add(productsRecipes);
  }

}
