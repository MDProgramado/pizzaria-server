package com.br.pizzaria.core.domain.repository;

import com.br.pizzaria.core.domain.models.Products;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductDAO {
    Products save(Connection connection, Products products) throws SQLException;

    List<Products> findAll(Connection connection) throws SQLException;

    Optional<Products> findById(Connection connection, UUID id) throws  SQLException;

    boolean deleteById(Connection connection, UUID id) throws  SQLException;


}
