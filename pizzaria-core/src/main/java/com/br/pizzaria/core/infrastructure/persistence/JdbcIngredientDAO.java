package com.br.pizzaria.core.infrastructure.persistence;

import com.br.pizzaria.core.domain.models.Ingredients;
import com.br.pizzaria.core.domain.repository.IngredientDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcIngredientDAO implements IngredientDAO {

    private static final String BASE_SELECT_COLUMNS =
            "SELECT id, tenant_id, name, unit_measure, current_balance, min_threshold, created_at, updated_at, deleted_at " +
                    "FROM ingredients";


    @Override
    public Ingredients save(Connection connection, Ingredients ingredient) throws SQLException {
        String sql = """
            INSERT INTO ingredients (tenant_id, name, unit_measure, current_balance, min_threshold)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id, created_at, updated_at
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, ingredient.getTenant_id());
            stmt.setString(2, ingredient.getName());
            stmt.setString(3, ingredient.getUnit_measure());
            stmt.setBigDecimal(4, ingredient.getCurrent_balance());
            stmt.setBigDecimal(5, ingredient.getMin_threshold());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ingredient.setId(rs.getObject("id", UUID.class));
                    ingredient.setCreated_at(rs.getObject("created_at", OffsetDateTime.class));
                    ingredient.setUpdated_at(rs.getObject("updated_at", OffsetDateTime.class));
                }
            }
        }
        return ingredient;
    }

    @Override
    public Optional<Ingredients> findById(Connection connection, UUID id) throws SQLException {

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
    public List<Ingredients> findAll(Connection connection) throws SQLException {
        String sql = BASE_SELECT_COLUMNS + "WHERE deleted_at IS NULL ORDER BY name ASC";
        List<Ingredients> ingredientsList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ingredientsList.add(mapResultSetToIngredient(rs));
            }
        }
        return ingredientsList;
    }

    @Override
    public boolean deleteById(Connection connection, UUID id) throws SQLException{
        String sql = "UPDATE ingredients SET deleted_at = CURRENT_TIMESTAMP WHERE id = ? AND deleted_at IS NULL";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setObject(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }


    @Override
    public boolean debitBalance(Connection connection, UUID id, BigDecimal amount) throws SQLException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O valor do débito deve ser maior que zero.");
        }

        String sql = "UPDATE ingredients SET current_balance = current_balance - ? WHERE id = ? AND deleted_at IS NULL";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setBigDecimal(1, amount);
            stmt.setObject(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private Ingredients mapResultSetToIngredient(ResultSet rs) throws SQLException {
        Ingredients ingredient = new Ingredients();
        ingredient.setId(rs.getObject("id", UUID.class));
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