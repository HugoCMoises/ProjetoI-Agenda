package br.com.agenda.exception;

public class ContatoNaoEncontradoException extends RuntimeException {
    public ContatoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
