package com.pizzaria.core.domain.repository;

import com.pizzaria.core.domain.models.ProductsRecipes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankListProductsRecipes {
    private static  final List<ProductsRecipes> listaReceitaProdutos = new ArrayList<>();

    public static List<ProductsRecipes> getAllProductsRecipes(){
        return listaReceitaProdutos;
    }

    public static ProductsRecipes getProductsId(UUID id){
        return listaReceitaProdutos.stream()
                .filter(i -> i.getProduct_Id().equals(id))
                .findFirst()
                .orElse(null);
    }

  public static boolean save(BigDecimal quantityRequired){

        ProductsRecipes productsRecipes = new ProductsRecipes(quantityRequired);

        return listaReceitaProdutos.add(productsRecipes);
  }

}
