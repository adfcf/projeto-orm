package io.github.adfcf.gestaocontratos.bd.daos;

import io.github.adfcf.gestaocontratos.entidades.Cliente;
import io.github.adfcf.gestaocontratos.entidades.Contrato;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    CREATE TABLE contratos (

        id BIGINT NOT NULL AUTO_INCREMENT,
        cliente_id BIGINT NOT NULL,
        redacao MEDIUMTEXT,
        ultima_atualizacao DATE,

        PRIMARY KEY(id),
        FOREIGN KEY(cliente_id) REFERENCES clientes(id)

    );
*/

public final class ContratoDao extends Dao<Contrato> {

    public static final String TABELA = "contratos";

    private static ContratoDao instance;
    private ContratoDao() {}

    public static ContratoDao obter() {
        if (instance == null) {
            instance = new ContratoDao();
        }
        return instance;
    }

    @Override
    protected void preencherDados(PreparedStatement s, Contrato e) {

        try {

            // Colocando ID do cliente.
            s.setLong(1, e.getContratante().getId());

            // Colocando a redação do contrato.
            s.setString(2, e.getRedacao());

            // Colocando a data da última atualização do contrato.
            s.setObject(3, e.getUltimaAtualizacao(), JDBCType.DATE);

            // Se o ID está presente, então se trata de uma atualização.
            if (e.getId() != null) {
                s.setLong(4, e.getId());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected Contrato extrairObjeto(ResultSet resultSet) {

        Contrato c = null;
        try {

            final long id = resultSet.getLong(1);

            final long idContratante = resultSet.getLong(2);
            final Cliente contratante = ClienteDao.obter().encontrarPorId(idContratante);

            final String redacao = resultSet.getString(3);

            final LocalDate ultimaAtualizacao = resultSet.getDate(4).toLocalDate();

            c = new Contrato(
                    id,
                    contratante,
                    redacao,
                    ultimaAtualizacao
            );

        } catch (SQLException ex) {
            Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;

    }

    @Override
    protected void preencherId(PreparedStatement s, long id) {
        try {
            s.setLong(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(ContratoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected String obterComandoInsercao() {
        return "INSERT INTO " + TABELA + " (cliente_id, redacao, ultima_atualizacao) VALUES (?, ?, ?);";
    }

    @Override
    protected String obterComandoAtualizacao() {
        return "UPDATE " + TABELA + " SET cliente_id = ?, redacao = ?, ultima_atualizacao = ? WHERE id = ?;";
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
