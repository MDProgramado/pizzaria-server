package com.br.pizzaria.core.domain.exceptions;

public class PersistenceException extends  RuntimeException{

    public PersistenceException(String message){
        super(message);
    }

    public PersistenceException(String message, Throwable cause){
        super(message, cause);
    }
}
