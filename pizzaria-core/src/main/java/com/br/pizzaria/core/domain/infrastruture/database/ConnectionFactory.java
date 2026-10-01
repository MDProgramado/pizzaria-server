package com.br.pizzaria.core.domain.infrastruture.database;

public class ConnectionFactory {

    private final String URL = "jdbc:postgresql://localhost:5435/pizzaria_db";
    final private String USUARIO = "app_pizzaria";
    final private String SENHA = System.getenv("DB_PASSWORD");
}
