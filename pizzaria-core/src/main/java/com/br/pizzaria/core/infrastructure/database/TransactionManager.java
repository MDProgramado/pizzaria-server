package com.br.pizzaria.core.infrastructure.database;

import com.br.pizzaria.core.domain.exceptions.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class TransactionManager {

    private final ConnectionFactory connectionFactory;

    public TransactionManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @FunctionalInterface
    public interface TransactionalOperation<T> {
        T execute(Connection connection) throws SQLException;
    }

    private void defineTenant(Connection connection, UUID tenantId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT set_config('app.current_tenant', ?, true)")) {
            stmt.setString(1, tenantId.toString());
            stmt.execute();
        }
    }

    public <T> T executeInTransaction(UUID tenantId, TransactionalOperation<T> operation) {


        if (tenantId == null) {
            throw new IllegalArgumentException("O tenantId não pode ser nulo para operações no banco de dados.");
        }

        try (Connection connection = connectionFactory.getConnection()) {

            connection.setAutoCommit(false);

            try {
                defineTenant(connection, tenantId);

                T result = operation.execute(connection);

                connection.commit();
                return result;


            } catch (Exception e) {

                connection.rollback();


                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new PersistenceException("Erro na execução da transação: " + e.getMessage(), e);
            }


        } catch (SQLException e) {
            throw new PersistenceException("Falha na infraestrutura de banco de dados: " + e.getMessage(), e);
        }
    }
}