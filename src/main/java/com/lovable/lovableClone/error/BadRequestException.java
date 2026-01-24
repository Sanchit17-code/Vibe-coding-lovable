package com.lovable.lovableClone.error;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message){
        super(message);
    }
}
