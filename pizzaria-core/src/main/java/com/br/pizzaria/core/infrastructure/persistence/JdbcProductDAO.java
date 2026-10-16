package com.br.pizzaria.core.infrastructure.persistence;

import com.br.pizzaria.core.domain.models.Products;
import com.br.pizzaria.core.domain.repository.ProductDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcProductDAO implements ProductDAO {


    private static final String BASE_SELECT_COLUMNS =
            "SELECT id, tenant_id, name, base_price, is_active, created_at, updated_at, deleted_at FROM products";

    @Override
    public Products save(Connection connection, Products product) throws SQLException {

        String sql = """
                INSERT INTO products (tenant_id, name, base_price, is_active)
                VALUES (?, ?, ?, ?)
                RETURNING id, created_at, updated_at
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, product.getTenant_id());
            stmt.setString(2, product.getName());
            stmt.setBigDecimal(3, product.getBase_price());
            stmt.setBoolean(4, product.isIs_active());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    product.setId(rs.getObject("id", UUID.class));
                    product.setCreated_at(rs.getObject("created_at", OffsetDateTime.class));
                    product.setUpdated_at(rs.getObject("updated_at", OffsetDateTime.class));
                }
            }
        }
        return product;
    }

    @Override
    public Optional<Products> findById(Connection connection, UUID id) throws SQLException {

        String sql = BASE_SELECT_COLUMNS + " WHERE id = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToProduct(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Products> findAll(Connection connection) throws SQLException {

        String sql = BASE_SELECT_COLUMNS + " WHERE deleted_at IS NULL ORDER BY name ASC";
        List<Products> productsList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                productsList.add(mapResultSetToProduct(rs));
            }
        }
        return productsList;
    }

    @Override
    public boolean deleteById(Connection connection, UUID id) throws SQLException {
        String sql = "UPDATE products SET deleted_at = CURRENT_TIMESTAMP WHERE id = ? AND deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private Products mapResultSetToProduct(ResultSet rs) throws SQLException {
        Products product = new Products();
        product.setId(rs.getObject("id", UUID.class));
        product.setTenant_id(rs.getObject("tenant_id", UUID.class));
        product.setName(rs.getString("name"));
        product.setBase_price(rs.getBigDecimal("base_price"));
        product.setIs_active(rs.getBoolean("is_active"));
        product.setCreated_at(rs.getObject("created_at", OffsetDateTime.class));
        product.setUpdated_at(rs.getObject("updated_at", OffsetDateTime.class));
        product.setDeleted_at(rs.getObject("deleted_at", OffsetDateTime.class));
        return product;
    }
}