package com.br.pizzaria.core.domain.repository;

import com.br.pizzaria.core.domain.models.ProductsRecipes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ProductRecipeDAO {
     ProductsRecipes            save(Connection connection, ProductsRecipes productsRecipes)      throws SQLException;
     List<ProductsRecipes>      findByProductId(Connection connection, UUID productId)            throws SQLException;
     boolean                    delete(Connection connection, UUID productId, UUID ingredientId)  throws  SQLException;


}
