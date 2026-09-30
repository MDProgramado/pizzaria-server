package com.br.pizzaria.core.domain.services;

import com.br.pizzaria.core.domain.models.Ingredients;
import com.br.pizzaria.core.domain.models.ProductsRecipes;
import com.br.pizzaria.core.domain.models.StockCheckResult;
import com.br.pizzaria.core.domain.repository.BankListIngriendts;
import com.br.pizzaria.core.domain.repository.BankListProductsRecipes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeService {

    private static final Object lock = new Object();


    private BigDecimal calculateTotalRequired(ProductsRecipes recipe, Integer desiredQuantity) {

        BigDecimal quantityAsBigDecimal = BigDecimal.valueOf(desiredQuantity);

        return recipe.getQuantityRequired().multiply(quantityAsBigDecimal);
    }

    public StockCheckResult checkStockForProduct(UUID productId, Integer desiredQuantity) {

        List<ProductsRecipes> recipes = BankListProductsRecipes.getRecipesByProductId(productId);

        if (recipes.isEmpty()) {
            return StockCheckResult.insufficient(List.of("Produto não possui receita cadastrada."));
        }

        List<String> missingIngredients = new ArrayList<>();

        for (ProductsRecipes recipe : recipes) {
            UUID ingredientId = recipe.getIngredientId();



            if (ingredientId == null) {
                missingIngredients.add("Receita com ingrediente não associado.");
                continue;
            }

            Ingredients currentIngredient = BankListIngriendts.getIngredientsId(ingredientId);

            if (currentIngredient == null) {
                missingIngredients.add("Ingrediente não encontrado no cadastro (ID: " + ingredientId + ")");
                continue;
            }

            BigDecimal totalRequired = calculateTotalRequired(recipe, desiredQuantity);

            if (currentIngredient.getCurrent_balance().compareTo(totalRequired) < 0) {
                String errorMsg = String.format(
                        "Ingrediente '%s' insuficiente. Necessário: %s %s, Em Estoque: %s %s",
                        currentIngredient.getName(),
                        totalRequired,
                        currentIngredient.getUnit_measure(),
                        currentIngredient.getCurrent_balance(),
                        currentIngredient.getUnit_measure()
                );
                missingIngredients.add(errorMsg);
            }
        }

        if (missingIngredients.isEmpty()) {
            return StockCheckResult.success();
        } else {
            return StockCheckResult.insufficient(missingIngredients);
        }
    }


    public StockCheckResult processInventoryConsumption(UUID productId, Integer desiredQuantity){

        synchronized (lock) {

            StockCheckResult result = checkStockForProduct(productId, desiredQuantity);

            if (!result.hasStock()) {
                return result;
            }

            List<ProductsRecipes> recipes = BankListProductsRecipes.getRecipesByProductId(productId);


            List<String> falhasNoDebito = new ArrayList<>();

            for (ProductsRecipes recipe : recipes) {
                BigDecimal totalNecessario = calculateTotalRequired(recipe, desiredQuantity);


                boolean debitoRealizado = BankListIngriendts.debitBalance(recipe.getIngredientId(), totalNecessario);

                if (!debitoRealizado) {
                    falhasNoDebito.add("Falha sistêmica: O ingrediente ID " + recipe.getIngredientId() + " sumiu durante o débito.");
                }
            }


            if (!falhasNoDebito.isEmpty()) {
                return StockCheckResult.insufficient(falhasNoDebito);
            }

            return StockCheckResult.success();
        }
    }
}
