package io.github.adfcf.gestaocontratos.bd.daos;

import io.github.adfcf.gestaocontratos.entidades.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

    /*
         CREATE TABLE clientes (

        id BIGINT NOT NULL AUTO_INCREMENT,
        cpf BIGINT,
        nome VARCHAR(45),

        PRIMARY KEY(id)

        );
    */

public final class ClienteDao extends Dao<Cliente> {

    public static final String TABELA = "clientes";

    private static ClienteDao instance;
    private ClienteDao() {}

    public static ClienteDao obter() {
        if (instance == null) {
            instance = new ClienteDao();
        }
        return instance;
    }

    @Override
    protected void preencherDados(PreparedStatement s, Cliente e) {

        try {

            // Colocando CPF do cliente.
            s.setLong(1, e.getCpf());

            // Colocando o nome do cliente.
            s.setString(2, e.getNome());

            // Se o ID está presente, então se trata de uma atualização.
            if (e.getId() != null) {
                s.setLong(3, e.getId());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected Cliente extrairObjeto(ResultSet resultSet) {

        Cliente c = null;
        try {

            final long id = resultSet.getLong(1);

            final Long cpf = resultSet.getLong(2);

            final String nome = resultSet.getString(3);

            c = new Cliente(
                    id,
                    cpf,
                    nome
            );

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;

    }

    @Override
    protected void preencherId(PreparedStatement s, long id) {
        try {
            s.setLong(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected String obterComandoInsercao() {
        return "INSERT INTO " + TABELA + " (cpf, nome) VALUES (?, ?);";
    }

    @Override
    protected String obterComandoAtualizacao() {
        return "UPDATE " + TABELA + " SET cpf = ?, nome = ? WHERE id = ?;";
    }

    @Override
    protected String obterComandoBuscaPorId() {
        return "SELECT * FROM " + TABELA + " WHERE id = ?;";
    }

    @Override
    protected String obterComandoRecuperarTodas() {
        return "SELECT * FROM " + TABELA + ";";
    }

    @Override
    protected String obterComandoDelecao() {
        return "DELETE FROM " + TABELA + " WHERE id = ?;";
    }

}
