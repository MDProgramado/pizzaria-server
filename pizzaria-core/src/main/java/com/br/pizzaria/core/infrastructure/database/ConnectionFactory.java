package com.br.pizzaria.core.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private final String urlDb;
    private final String usuario;
    private final String senha;

   public ConnectionFactory(){
       this.urlDb = readVariable("DB_URL");
       this.usuario = readVariable("DB_USER");
       this.senha = readVariable("DB_PASSWORD");
   }

   private String readVariable(String nameVariable){
       String valor = System.getenv(nameVariable);

       if (valor == null || valor.trim().isBlank()){
           throw new IllegalStateException(
                   "❌ ERRO CRÍTICO: Configuração ausente. " +
                           "A variável de ambiente '" + nameVariable + "' não foi encontrada ou está vazia."
           );
       }

       return  valor;
   }

   public Connection getConnection() throws SQLException {
       return DriverManager.getConnection(urlDb, usuario, senha);
   }


}
