package br.com.anhembi.service.exception;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException(String message){
        super(message);
    }
}
