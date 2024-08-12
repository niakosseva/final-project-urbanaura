package com.example.UrbanAura.exceptions;



public class EmailAndUsernameAlreadyExistsException extends RuntimeException{

    public EmailAndUsernameAlreadyExistsException(String message) {
       super(message);
    }


}
