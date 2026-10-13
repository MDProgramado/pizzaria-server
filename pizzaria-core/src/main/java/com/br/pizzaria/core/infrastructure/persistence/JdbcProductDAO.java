package com.br.pizzaria.core.infrastructure.persistence;


import com.br.pizzaria.core.domain.models.Ingredients;
import com.br.pizzaria.core.domain.models.Products;
import com.br.pizzaria.core.domain.repository.ProductDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

public class JdbcProductDAO implements ProductDAO {

    private static final String BASE_SELECT_COLUMNS =
            """
             SELECT id, tenant_id, name, base_price, is_active, created_at, , updated_at, deleted_at FROM products\s
           \s""";


    @Override
     public  Products save(Connection connection, Products products) throws SQLException {

        String sql = """
                INSERT INTO  products (tenant_id, name, base_price)
                VALUES (?,?,?)
                RETURNING id, created_at, updated_at
                """;

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setObject(1, products.getTenant_id());
            stmt.setString(2, products.getName());
            stmt.setBigDecimal(3, products.getBase_price());

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    products.setId(rs.getObject("id", UUID.class));
                    products.setCreated_at(rs.getObject("created_at", OffsetDateTime.class));
                    products.setUpdated_at(rs.getObject("updated_at", OffsetDateTime.class));
                }
            }
        }
        return products;
    }

    @Override
    public Optional<Products> findById(Connection connection, UUID id) throws SQLException {
        String sql = BASE_SELECT_COLUMNS + " WHERE id = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToIngredient(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Products> findAll(Connection connection) throws SQLException{
        return;
    }
    @Override
    public boolean deleteById(Connection connection, UUID id) throws SQLException{
        return false;

    }

    @Override
    public  boolean debitBalance(Connection connection, UUID id, BigDecimal amount) throws SQLException{
        return false;
    }

    private Products mapResultSetToProducts(ResultSet rs) throws SQLException {
        Products products = new Products();
        products.setId(rs.getObject("id", UUID.class));
        ingredient.setTenant_id(rs.getObject("tenant_id", UUID.class));
        ingredient.setName(rs.getString("name"));
        ingredient.setUnit_measure(rs.getString("unit_measure"));
        ingredient.setCurrent_balance(rs.getBigDecimal("current_balance"));
        ingredient.setMin_threshold(rs.getBigDecimal("min_threshold"));
        ingredient.setCreated_at(rs.getObject("created_at", OffsetDateTime.class));
        ingredient.setUpdated_at(rs.getObject("updated_at", OffsetDateTime.class));
        ingredient.setDeleted_at(rs.getObject("deleted_at", OffsetDateTime.class));
        return ingredient;
    }


}
