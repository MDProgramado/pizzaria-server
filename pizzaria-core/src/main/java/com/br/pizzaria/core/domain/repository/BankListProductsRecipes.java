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
                .filter(i -> i.getProduct_Id().equals(id))
                .toList();
    }

  public static boolean save(Products productId, Ingredients ingredientId, BigDecimal quantityRequired){
        ProductsRecipes productsRecipes = new ProductsRecipes(productId, ingredientId,quantityRequired);
        return listaReceitaProdutos.add(productsRecipes);
  }

}
