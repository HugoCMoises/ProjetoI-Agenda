package br.com.agenda.dao;

import br.com.agenda.model.Contato;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ContatoDAO {
    private final JdbcTemplate jdbcTemplate;

    public ContatoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Contato salvar(Contato contato) {
        String sql = "INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?) RETURNING id";
        Integer id = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                contato.getNome(),
                contato.getTelefone(),
                contato.getEmail()
        );

        contato.setId(id);
        return contato;
    }

    public List<Contato> listar() {
        String sql = "SELECT id, nome, telefone, email FROM contatos ORDER BY nome";
        return jdbcTemplate.query(sql, contatoMapper());
    }

    public Optional<Contato> buscarPorNome(String nome) {
        String sql = "SELECT id, nome, telefone, email FROM contatos WHERE nome ILIKE ? ORDER BY nome LIMIT 1";

        try {
            Contato contato = jdbcTemplate.queryForObject(sql, contatoMapper(), "%" + nome + "%");
            return Optional.ofNullable(contato);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean atualizar(Contato contato) {
        String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        int linhas = jdbcTemplate.update(
                sql,
                contato.getNome(),
                contato.getTelefone(),
                contato.getEmail(),
                contato.getId()
        );

        return linhas > 0;
    }

    public boolean removerPorNome(String nome) {
        String sql = "DELETE FROM contatos WHERE LOWER(nome) = LOWER(?)";
        return jdbcTemplate.update(sql, nome) > 0;
    }

    private RowMapper<Contato> contatoMapper() {
        return (rs, rowNum) -> new Contato(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email")
        );
    }
}
