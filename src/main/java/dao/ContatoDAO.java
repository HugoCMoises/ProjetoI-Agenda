package dao;

import model.Contato;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContatoDAO {

    public void inserir(Contato contato) {
        // PreparedStatement evita montar SQL manualmente com os dados digitados.
        String sql = "INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?)";

        try (Connection conexao = DatabaseConnection.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir contato no banco de dados.");
        }
    }

    public boolean removerPorNome(String nome) {
        String sql = "DELETE FROM contatos WHERE LOWER(nome) = LOWER(?)";

        try (Connection conexao = DatabaseConnection.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover contato do banco de dados.");
        }
    }

    public Optional<Contato> buscarPorNome(String nome) {
        String sql = "SELECT nome, telefone, email FROM contatos WHERE LOWER(nome) = LOWER(?)";

        try (Connection conexao = DatabaseConnection.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Contato(
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email")
                ));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contato no banco de dados.");
        }
    }

    public List<Contato> listar() {
        String sql = "SELECT nome, telefone, email FROM contatos ORDER BY nome";
        List<Contato> contatos = new ArrayList<>();

        try (Connection conexao = DatabaseConnection.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                contatos.add(new Contato(
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email")
                ));
            }

            return contatos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contatos do banco de dados.");
        }
    }
}
