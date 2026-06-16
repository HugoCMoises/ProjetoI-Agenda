package br.com.agenda.service;

import br.com.agenda.dao.ContatoDAO;
import br.com.agenda.exception.CampoVazioException;
import br.com.agenda.exception.ContatoNaoEncontradoException;
import br.com.agenda.model.Contato;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaTelefonica {
    private final ContatoDAO contatoDAO;

    public AgendaTelefonica(ContatoDAO contatoDAO) {
        this.contatoDAO = contatoDAO;
    }

    public void adicionarContato(Contato contato) {
        validarContato(contato);
        prepararContato(contato);
        contatoDAO.salvar(contato);
    }

    public void removerContato(String nome) {
        validarNomeBusca(nome);

        if (!contatoDAO.removerPorNome(nome.trim())) {
            throw new ContatoNaoEncontradoException("Contato nao encontrado.");
        }
    }

    public Contato buscarContato(String nome) {
        validarNomeBusca(nome);

        return contatoDAO.buscarPorNome(nome.trim())
                .orElseThrow(() -> new ContatoNaoEncontradoException("Contato nao encontrado."));
    }

    public List<Contato> listarContatos() {
        return contatoDAO.listar();
    }

    public void atualizarContato(Integer id, Contato contato) {
        if (id == null || id <= 0) {
            throw new CampoVazioException("ID invalido para atualizar.");
        }

        validarContato(contato);
        prepararContato(contato);
        contato.setId(id);

        if (!contatoDAO.atualizar(contato)) {
            throw new ContatoNaoEncontradoException("Contato nao encontrado.");
        }
    }

    private void validarContato(Contato contato) {
        if (contato == null) {
            throw new CampoVazioException("Preencha os dados do contato.");
        }

        validarNome(contato.getNome());
        validarTelefone(contato.getTelefone());
        validarEmail(contato.getEmail());
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new CampoVazioException("Preencha o nome corretamente.");
        }

        String nomeLimpo = nome.trim();

        if (nomeLimpo.length() < 3) {
            throw new CampoVazioException("O nome deve ter pelo menos 3 caracteres.");
        }

        if (nomeLimpo.matches(".*\\d.*")) {
            throw new CampoVazioException("O nome nao pode conter numeros.");
        }
    }

    private void validarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new CampoVazioException("Preencha o telefone corretamente.");
        }

        String telefoneLimpo = telefone.trim();

        if (!telefoneLimpo.matches("\\d+")) {
            throw new CampoVazioException("O telefone deve conter apenas numeros.");
        }

        if (telefoneLimpo.length() < 10 || telefoneLimpo.length() > 11) {
            throw new CampoVazioException("O telefone deve ter entre 10 e 11 digitos.");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new CampoVazioException("Digite um e-mail valido.");
        }

        String emailLimpo = email.trim();

        if (!emailLimpo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new CampoVazioException("Digite um e-mail valido.");
        }
    }

    private void validarNomeBusca(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new CampoVazioException("Digite um nome para buscar.");
        }
    }

    private void prepararContato(Contato contato) {
        contato.setNome(contato.getNome().trim());
        contato.setTelefone(contato.getTelefone().trim());
        contato.setEmail(contato.getEmail().trim());
    }
}
