package com.br.pizzaria.core.infrastructure.persistence;


import com.br.pizzaria.core.domain.models.Products;
import com.br.pizzaria.core.domain.repository.ProductDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Optional;
import java.util.UUID;

public class JdbcProductDAO implements ProductDAO {

    private static final String BASE_SELECT_COLUMNS =
            """
             SELECT id, tenant_id, name, unit_measure, current_balance, min_threshold, created_at, updated_at, deleted_at FROM ingredients\s
           \s""";


    @Override
     public  Products save(Connection connection, Products products){
        return products;
    }

    @Override
    public Optional<Products> findAll(Connection connection){
        return Optional.empty();
    }
    @Override
    public boolean deleteById(Connection connection, UUID id){
        return false;

    }

    @Override
    public  boolean debitBalance(Connection connection, UUID id, BigDecimal amount){
        return false;
    }



}
