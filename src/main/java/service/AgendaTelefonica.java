package service;

import dao.ContatoDAO;
import exception.CampoVazioException;
import exception.ContatoNaoEncontradoException;
import model.Contato;

import java.util.List;

public class AgendaTelefonica {
    private final ContatoDAO contatoDAO = new ContatoDAO();

    public void adicionarContato(Contato contato) {
        validarContato(contato);
        prepararContato(contato);
        contatoDAO.inserir(contato);
    }

    public void removerContato(String nome) {
        validarNome(nome);

        if (!contatoDAO.removerPorNome(nome.trim())) {
            throw new ContatoNaoEncontradoException("Contato nao encontrado.");
        }
    }

    public Contato buscarContato(String nome) {
        validarNome(nome);

        return contatoDAO.buscarPorNome(nome.trim())
                .orElseThrow(() -> new ContatoNaoEncontradoException("Contato nao encontrado."));
    }

    public List<Contato> listarContatos() {
        return contatoDAO.listar();
    }

    private void validarContato(Contato contato) {
        if (contato == null) {
            throw new CampoVazioException("Os dados do contato sao obrigatorios.");
        }

        validarNome(contato.getNome());
        validarCampo(contato.getTelefone(), "Telefone");
        validarCampo(contato.getEmail(), "Email");
    }

    private void validarNome(String nome) {
        validarCampo(nome, "Nome");
    }

    private void validarCampo(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new CampoVazioException(campo + " nao pode ficar vazio.");
        }
    }

    private void prepararContato(Contato contato) {
        // Antes de salvar, removo espacos que o usuario pode digitar sem querer.
        contato.setNome(contato.getNome().trim());
        contato.setTelefone(contato.getTelefone().trim());
        contato.setEmail(contato.getEmail().trim());
    }
}
