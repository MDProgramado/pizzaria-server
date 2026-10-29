package com.br.pizzaria.core.infrastructure.persistence;

import com.br.pizzaria.core.domain.models.Products;
import com.br.pizzaria.core.domain.models.ProductsRecipes;
import com.br.pizzaria.core.domain.repository.ProductRecipeDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcProductRecipeDAO implements ProductRecipeDAO {
    private static final String BASE_SELECT_COLUMNS =
            """
             SELECT id, productId, ingredientId, tenantId, quantityRequired\s
           \s""";

    @Override
    public ProductsRecipes save(Connection connection, ProductsRecipes productsRecipes) throws SQLException {
        ProductsRecipes recipes = new ProductsRecipes();

        String sql =
                """
                INSERT INTO (productId, ingredientId, quantityRequired, tenantId)
                """;
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setObject(1, productsRecipes.getProductId());
            stmt.setObject(2, productsRecipes.getIngredientId());
            stmt.setObject(3,productsRecipes.getTenantId());
            stmt.setBigDecimal(4, productsRecipes.getQuantityRequired());
        }

        return recipes;
    }



    @Override
    public  List<ProductsRecipes> findByProductId(Connection connection, UUID productId) throws SQLException{

        List<ProductsRecipes> list = new ArrayList<>();
        return list;
    }
    @Override

    public boolean  delete(Connection connection, UUID productId, UUID ingredientId)  throws  SQLException{

        return false;
    }
}
