package br.com.ogabrielfelipe.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(){
        super("user exists.");
    }
}
