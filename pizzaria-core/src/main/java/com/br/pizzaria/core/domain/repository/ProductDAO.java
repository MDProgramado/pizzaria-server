package com.br.pizzaria.core.domain.repository;

import com.br.pizzaria.core.domain.models.Products;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface ProductDAO {
    Products save(Connection connection, Products products) throws SQLException;

    Optional<Products> findAll(Connection connection) throws SQLException;

    boolean deleteById(Connection connection, UUID id) throws  SQLException;

    boolean debitBalance(Connection connection, UUID id, BigDecimal amount) throws  SQLException;
}
