package dao;

import connection.ConexaoMySQL;
import exception.AcomodacaoException;
import model.Acomodacao;
import model.Funcionario;
import model.Pessoa;

import java.sql.*;
import java.util.ArrayList;

public class AcomodacaoDAO implements DAO<Acomodacao> {

    // Métodos de interação com banco de dados

    public ArrayList<Acomodacao> selecionar() throws AcomodacaoException {
        try {
            String sql = "SELECT " +
                    "id, " +
                    "nome, " +
                    "valor_diaria, " +
                    "limite_hospedes, " +
                    "descricao, " +
                    "id_funcionario_responsavel " +
                    "FROM acomodacao";
            Statement declaracao = ConexaoMySQL.get().createStatement();
            ResultSet resultado = declaracao.executeQuery(sql);

            ArrayList<Acomodacao> acomodacaos = new ArrayList<>();
            while(resultado.next()) {
                Acomodacao acomodacao = new Acomodacao(
                        resultado.getLong("id"),
                        resultado.getString("nome"),
                        resultado.getDouble("valor_diaria"),
                        resultado.getInt("limite_hospedes"),
                        resultado.getString("descricao"),
                        getFuncionario(resultado.getLong("id_funcionario_responsavel"))
                );
                acomodacaos.add(acomodacao);
            }
            return acomodacaos;

        } catch (SQLException e) {
            throw new AcomodacaoException("Erro desconhecido! Por favor, tente novamente mais tarde.");
        }
    }

    public Boolean inserir(Acomodacao acomodacao) throws AcomodacaoException {
        try {
            String sql = "INSERT INTO acomodacao" +
                    "(nome, valor_diaria, limite_hospedes, descricao, id_funcionario_responsavel) " +
                    "VALUES (?,?,?,?,?)";

            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setString(1, acomodacao.getNome());
            preparacao.setDouble(2, acomodacao.getValorDiaria());
            preparacao.setInt(3, acomodacao.getLimiteHospedes());
            preparacao.setString(4, acomodacao.getDescricao());
            preparacao.setInt(5, 1);
            return preparacao.executeUpdate() > 0;

        } catch (Exception e) {
            throw new AcomodacaoException("Erro desconhecido! Por favor, tente novamente mais tarde.");
        }
    }

    public Boolean atualizar(Acomodacao acomodacao) throws AcomodacaoException {
        try {
            String sql = "UPDATE acomodacao " +
                    "SET " +
                    "nome = ?, " +
                    "valor_diaria = ? , " +
                    "limite_hospedes = ?, " +
                    "descricao = ?, " +
                    "id_funcionario_responsavel = ? " +
                    "WHERE id = ?";

            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setString(1, acomodacao.getNome());
            preparacao.setDouble(2, acomodacao.getValorDiaria());
            preparacao.setInt(3, acomodacao.getLimiteHospedes());
            preparacao.setString(4, acomodacao.getDescricao());
            preparacao.setInt(5, 1);
            preparacao.setLong(6, acomodacao.getId());
            return preparacao.executeUpdate() > 0;

        } catch (Exception e) {
            throw new AcomodacaoException("Erro desconhecido! Por favor, tente novamente mais tarde.");
        }
    }
    public Boolean deletar(Long id) throws AcomodacaoException {
        try {
            String sql = "DELETE FROM acomodacao WHERE id = ?";
            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setLong(1, id);
            return preparacao.executeUpdate() > 0;

        } catch (Exception e) {
            throw new AcomodacaoException("Erro desconhecido! Por favor, tente novamente mais tarde.");
        }
    }

    public Funcionario getFuncionario(Long id) throws AcomodacaoException {
        try {
            String sql = "SELECT " +
                    "id, " +
                    "id_pessoa, " +
                    "cargo, " +
                    "salario " +
                    "FROM funcionario " +
                    "WHERE id = ?";
            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setLong(1, id);
            ResultSet resultado = preparacao.executeQuery();

            if(resultado.next()) {
                Pessoa pessoa = getPessoa(resultado.getLong("id_pessoa"));
                return new Funcionario(
                        resultado.getLong("id"),
                        pessoa.getNomeCompleto(),
                        pessoa.getDataNascimento(),
                        pessoa.getDocumento(),
                        pessoa.getPais(),
                        pessoa.getEstado(),
                        pessoa.getCidade(),
                        resultado.getLong("id_pessoa"),
                        resultado.getString("cargo"),
                        resultado.getDouble("salario")
                );
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new AcomodacaoException("Erro desconhecido! Por favor, tente novamente mais tarde.");
        }
    }

    public Pessoa getPessoa(Long id) throws AcomodacaoException {
        try {
            String sql = "SELECT " +
                    "id, " +
                    "nome_completo, " +
                    "data_nascimento, " +
                    "documento, " +
                    "pais, " +
                    "estado, " +
                    "cidade " +
                    "FROM pessoa " +
                    "WHERE id = ?";
            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setLong(1, id);
            ResultSet resultado = preparacao.executeQuery();

            if(resultado.next()) {
                return new Pessoa(
                        resultado.getLong("id"),
                        resultado.getString("nome_completo"),
                        resultado.getDate("data_nascimento").toLocalDate(),
                        resultado.getString("documento"),
                        resultado.getString("pais"),
                        resultado.getString("estado"),
                        resultado.getString("cidade")
                );
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new AcomodacaoException("Erro desconhecido! Por favor, tente novamente mais tarde.");
        }
    }

    public Long targetId() throws AcomodacaoException {
        try {
            String sql = "SELECT " +
                    "id " +
                    "FROM acomodacao " +
                    "WHERE id NOT IN (1, 2)";

            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            ResultSet resultado = preparacao.executeQuery();

            if (resultado.next())
                return resultado.getLong("id");
            else
                return null;
        } catch (Exception e) {
            throw new AcomodacaoException("Erro desconhecido! Por favor, tente novamente mais tarde.");
        }
    }

    public Acomodacao selecionarPorId(Long id) throws AcomodacaoException {
        try {
            String sql = "SELECT " +
                    "id, " +
                    "nome, " +
                    "valor_diaria, " +
                    "limite_hospedes, " +
                    "descricao, " +
                    "id_funcionario_responsavel " +
                    "FROM acomodacao " +
                    "WHERE id = ?";
            PreparedStatement preparacao = ConexaoMySQL.get().prepareStatement(sql);
            preparacao.setLong(1, id);
            ResultSet resultado = preparacao.executeQuery();

            if(resultado.next()) {
                return new Acomodacao(
                        resultado.getLong("id"),
                        resultado.getString("nome"),
                        resultado.getDouble("valor_diaria"),
                        resultado.getInt("limite_hospedes"),
                        resultado.getString("descricao"),
                        new Funcionario(
                                resultado.getLong("id_funcionario_responsavel"),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null)
                );
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new AcomodacaoException("Erro desconhecido! Por favor, tente novamente mais tarde.");
        }
    }

}
