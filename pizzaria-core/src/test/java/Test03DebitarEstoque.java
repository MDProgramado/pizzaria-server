import com.br.pizzaria.core.domain.models.Ingredients;
import com.br.pizzaria.core.domain.models.Products;
import com.br.pizzaria.core.domain.models.StockCheckResult;
import com.br.pizzaria.core.domain.repository.BankListIngriendts;
import com.br.pizzaria.core.domain.repository.BankListProducts;
import com.br.pizzaria.core.domain.repository.BankListProductsRecipes;
import com.br.pizzaria.core.domain.services.RecipeService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Test03DebitarEstoque {
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

        // --- ESTADO INICIAL ---
        System.out.println("📊 Estoque INICIAL de " + ingredienteSalvo.getName() + ": " + ingredienteSalvo.getCurrent_balance() + ingredienteSalvo.getUnit_measure());
        System.out.println("-------------------------------------------------");

        // --- CÁLCULO MENTAL ESPERADO ---
        // 2 pizzas * 200g = 400g necessários.
        // Saldo é 500g. Tem que dar sucesso e sobrar 100g.

        StockCheckResult resultadoVenda1 = recipeService.processInventoryConsumption(produtoSalvo.getId(), 2);

        if (resultadoVenda1.hasStock()) {
            System.out.println("✅ [CENÁRIO 1: SUCESSO] Venda de 2 pizzas realizada com sucesso!");
        } else {
            System.out.println("❌ [CENÁRIO 1: FALHA] Venda bloqueada: " + resultadoVenda1);
        }

        // Pega o ingrediente atualizado do banco em memória
        Ingredients farinhaPosVenda1 = BankListIngriendts.getIngredientsId(ingredienteSalvo.getId());
        System.out.println("📊 Estoque APÓS CENÁRIO 1 (Esperado: 100): " + farinhaPosVenda1.getCurrent_balance() + farinhaPosVenda1.getUnit_measure());


        // --- CENÁRIO DE TESTE 2: TENTAR VENDER SEM ESTOQUE ---
        System.out.println("\n2. Testando pedido de mais 1 pizza (Estoque insuficiente):");
        System.out.println("-------------------------------------------------");

        // 1 pizza = 200g necessários.
        // Saldo atual = 100g. Tem que dar erro e NÃO alterar o saldo.

        StockCheckResult resultadoVenda2 = recipeService.processInventoryConsumption(produtoSalvo.getId(), 1);

        if (resultadoVenda2.hasStock()) {
            System.out.println("✅ [CENÁRIO 2: SUCESSO INDEVIDO] Venda realizada. (Isso é um erro do sistema!)");
        } else {
            System.out.println("❌ [CENÁRIO 2: BLOQUEIO CORRETO] Venda impedida. Motivo: " + resultadoVenda2);
        }

        Ingredients farinhaPosVenda2 = BankListIngriendts.getIngredientsId(ingredienteSalvo.getId());
        System.out.println("📊 Estoque APÓS CENÁRIO 2 (Esperado: 100): " + farinhaPosVenda2.getCurrent_balance() + farinhaPosVenda2.getUnit_measure());


    }
}
