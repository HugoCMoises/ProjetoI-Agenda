package br.com.agenda.exception;

public class CampoVazioException extends RuntimeException {
    public CampoVazioException(String mensagem) {
        super(mensagem);
    }
}
