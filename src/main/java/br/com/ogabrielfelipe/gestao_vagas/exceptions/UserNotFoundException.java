package br.com.ogabrielfelipe.gestao_vagas.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("user not found.");
    }
}
