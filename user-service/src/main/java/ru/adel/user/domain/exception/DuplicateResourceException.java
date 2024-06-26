package ru.adel.user.domain.exception;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String msg){
        super(msg);
    }
}
