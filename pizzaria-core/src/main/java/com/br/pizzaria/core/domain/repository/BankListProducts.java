package com.br.pizzaria.core.domain.repository;

import com.br.pizzaria.core.domain.models.Products;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankListProducts {
    private static final List<Products> listaProdutos = new ArrayList<>();

    public static List<Products> getAllProducts(){
        return listaProdutos.stream()
                .filter(products -> products.getDeleted_at() == null)
                .toList();
    }

    public static Products getProductsId(UUID id){
        return listaProdutos.stream()
                .filter(products -> products.getId().equals(id) && products.isIs_active()).
                findFirst()
                .orElse(null);
    }

    public static boolean save(String name, BigDecimal base_price, boolean is_active, LocalDateTime created_at) {
        Products products = new Products(name, base_price, is_active, created_at);
        return listaProdutos.add(products);
    }
    public static boolean deleteId(UUID id){
        Products products = getProductsId(id);
        if (products != null){
            products.setDeleted_at(LocalDateTime.now());
            return true;
        }
        return false;
    }
    public static boolean deleteName(String name) {
        return listaProdutos.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name) && i.isIs_active())
                .findFirst()
                .map(product -> {
                    product.setDeleted_at(LocalDateTime.now());
                    product.setIs_active(false);
                    return true;
                })
                .orElse(false);
    }

}
