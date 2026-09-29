
import com.br.pizzaria.core.domain.models.Ingredients;
import com.br.pizzaria.core.domain.models.Products;
import com.br.pizzaria.core.domain.models.ProductsRecipes;
import com.br.pizzaria.core.domain.repository.BankListIngriendts;
import com.br.pizzaria.core.domain.repository.BankListProducts;
import com.br.pizzaria.core.domain.repository.BankListProductsRecipes;


import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Test01ProdutoIngredientesReceita {
    public static void main(String[] args) {

        // 1. Salva o Produto
        BankListProducts.save("Pizza Calabresa", new BigDecimal("45.00"), true, LocalDateTime.now());

        // Pega o produto salvo na lista (o primeiro item adicionado)
        Products produtoSalvo = BankListProducts.getAllProducts().get(0);
        System.out.println("Produto salvo: " + produtoSalvo.getName());


        // 2. Salva o Ingrediente
        BankListIngriendts.save(
                "Farinha de trigo",
                "g",
                new BigDecimal("5.40"),
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

        System.out.println("Receita salva com sucesso? " + receitaSalva);
        System.out.println("Total de receitas: " + BankListProductsRecipes.getAllProductsRecipes().size());

        System.out.println("ID da Receita por produto: " + BankListProductsRecipes.getRecipesByProductId(produtoSalvo.getId()));


    }
}