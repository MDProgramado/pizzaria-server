package com.br.pizzaria.core.domain.repository;

import com.br.pizzaria.core.domain.models.Ingredients;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IngredientDAO {
    Ingredients save(Connection connection, Ingredients ingredient) throws SQLException;

    Optional<Ingredients> findById(Connection connection, UUID id) throws SQLException;

    List<Ingredients> findAll(Connection connection) throws SQLException;

    boolean deleteById(Connection connection, UUID id) throws SQLException;

    boolean debitBalance(Connection connection, UUID id, BigDecimal amount) throws SQLException;

}
