package com.pizzaria.core.domain.repository;

import com.pizzaria.core.domain.models.Ingredients;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BankListIngriendts {
    private static List<Ingredients> listaIngredients = new ArrayList<>();

    public static List<Ingredients> getAllIngredients(){

        return listaIngredients.stream()
                .filter(i -> i.getDeleted_at() == null)
                .toList();
    }


    public static Ingredients getIngredientsId(UUID id){
       return listaIngredients.stream()
               .filter(ingredients -> ingredients.getId().equals(id) && ingredients.getDeleted_at() == null)
               .findFirst()
               .orElse(null);
    }

    public static boolean save(String name, String unit_measure, BigDecimal current_balance, BigDecimal min_threshold, LocalDateTime created_at){
        Ingredients ingredients = new Ingredients(name, unit_measure, current_balance, min_threshold, created_at);
        return listaIngredients.add(ingredients);

    }

    public static boolean deleteId(UUID id){
        Ingredients ingredients = getIngredientsId(id);
        if (ingredients != null) {
            ingredients.setDeleted_at(LocalDateTime.now());
            return true;
        }
        return false;
    }
    public static boolean deleteName(String name) {
        return listaIngredients.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name) && i.getDeleted_at() == null)
                .findFirst()
                .map(ingredient -> {
                    ingredient.setDeleted_at(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }



}
