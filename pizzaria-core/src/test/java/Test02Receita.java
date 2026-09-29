import com.br.pizzaria.core.domain.models.Ingredients;
import com.br.pizzaria.core.domain.models.Products;
import com.br.pizzaria.core.domain.models.StockCheckResult;
import com.br.pizzaria.core.domain.repository.BankListIngriendts;
import com.br.pizzaria.core.domain.repository.BankListProducts;
import com.br.pizzaria.core.domain.repository.BankListProductsRecipes;
import com.br.pizzaria.core.domain.services.RecipeService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Test02Receita {

    public static void main(String[] args) {



        BankListProducts.save("Pizza Calabresa", new BigDecimal("45.00"), true, LocalDateTime.now());


        Products produtoSalvo = BankListProducts.getAllProducts().get(0);
        System.out.println("Produto salvo: " + produtoSalvo.getName());


        // 2. Salva o Ingrediente
        BankListIngriendts.save(
                "Farinha de trigo",
                "g",
                new BigDecimal("500"),
                new BigDecimal("3"),
                LocalDateTime.now()
        );

        // Pega o ingrediente salvo na lista
        Ingredients ingredienteSalvo = BankListIngriendts.getAllIngredients().get(0);
        System.out.println("Ingrediente salvo: " + ingredienteSalvo.getName());


        // 3. Salva a Receita associando o Produto e o Ingrediente
        var receitaSalva = BankListProductsRecipes.save(
                produtoSalvo,
                ingredienteSalvo,
                new BigDecimal("200") // quantidade necessária
        );



        var recipeService = new RecipeService();

        System.out.println("\n--- TESTE DE CHECAGEM DE ESTOQUE ---");
        System.out.println("\n1. Testando pedido de 2 pizzas:");
        StockCheckResult result = recipeService.checkStockForProduct(produtoSalvo.getId(), 2);

        if (result.hasStock()){
            System.out.println("✅ Estoque suficiente!");
        }
        else {
            System.out.println("❌ Faltou estoque:");
            result.missingIngredients().forEach(System.out::println);
        }


        // Cenário 2: Quantidade absurda (999999 pizzas)
        System.out.println("\n2. Testando pedido de 999999 pizzas:");
        StockCheckResult resultadoExcessivo = recipeService.checkStockForProduct(produtoSalvo.getId(), 999999);
        if (resultadoExcessivo.hasStock()) {
            System.out.println("✅ Estoque suficiente!");
        } else {
            System.out.println("❌ Faltou estoque:");
            resultadoExcessivo.missingIngredients().forEach(System.out::println);
        }
    }
}
