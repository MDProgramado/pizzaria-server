package com.br.pizzaria.core.domain.repository;

import com.br.pizzaria.core.domain.models.Ingredients;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class BankListIngriendts  {
    public static List<Ingredients> listaIngredients = new ArrayList<>();

    public static List<Ingredients> getAllIngredients(){

        return listaIngredients.stream()
                .filter(i -> i.getDeleted_at() == null)
                .toList();
    }


    public static Ingredients getIngredientsId(UUID id){
       return listaIngredients.stream()
               .filter(ingredient -> ingredient.getId().equals(id) && ingredient.getDeleted_at() == null)
               .findFirst().orElse(null);
    }


    public static List<Ingredients> save(String name, String unit_measure, BigDecimal current_balance, BigDecimal min_threshold, LocalDateTime created_at){
        Ingredients ingredients = new Ingredients(name, unit_measure, current_balance, min_threshold, created_at);
        listaIngredients.add(ingredients);
        return listaIngredients;

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


    public static boolean debitBalance(UUID ingrendientId, BigDecimal quantity){

       Ingredients ingredient = getIngredientsId(ingrendientId);

       if (ingredient != null){
           BigDecimal novoSaldo = ingredient.getCurrent_balance().subtract(quantity);

           ingredient.setCurrent_balance(novoSaldo);
           ingredient.setUpdated_at(LocalDateTime.now());

           return true;
       }
        return false;
    }

}


