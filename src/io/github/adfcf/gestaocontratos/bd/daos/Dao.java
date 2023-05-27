package io.github.adfcf.gestaocontratos.bd.daos;

import io.github.adfcf.gestaocontratos.bd.Conexao;
import io.github.adfcf.gestaocontratos.entidades.Entidade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class Dao<T extends Entidade> implements IDao<T> {

    protected abstract void preencherDados(PreparedStatement s, T t);
    protected abstract void preencherId(PreparedStatement s, long id);

    protected abstract T extrairObjeto(ResultSet rs);

    protected abstract String obterComandoInsercao();
    protected abstract String obterComandoAtualizacao();
    protected abstract String obterComandoBuscaPorId();
    protected abstract String obterComandoRecuperarTodas();
    protected abstract String obterComandoDelecao();

    private Long inserir(T t) {

        final var db = Conexao.obter();

        try (var statement = db.prepareStatement(obterComandoInsercao(), Statement.RETURN_GENERATED_KEYS)) {

            preencherDados(statement, t);
            // System.out.println("SQL: " + statement.toString());

            statement.executeUpdate();
            final var keys = statement.getGeneratedKeys();

            if (keys.first()) {
                t.setId(keys.getInt(1));
            }

        } catch (SQLException ignored) {}

        return t.getId();

    }

    private Long atualizar(T t) {

        final var db = Conexao.obter();

        try (var statement = db.prepareStatement(obterComandoAtualizacao(), Statement.RETURN_GENERATED_KEYS)) {

            preencherDados(statement, t);
            // System.out.println("SQL: " + statement.toString());

            statement.executeUpdate();

        } catch (SQLException ignored) {}

        return t.getId();

    }

    @Override
    public final Long inserirOuAtualizar(T t) {

        // Se não possui ID, deve ser inserido e um novo ID assinalado.
        if (t.getId() == null) {
            return inserir(t);
        }

        // A entidade é atualizada e seu ID é retornado.
        return atualizar(t);

    }

    @Override
    public final T encontrarPorId(long id) {

        final var bd = Conexao.obter();

        T t = null;

        try (var statement = bd.prepareStatement(obterComandoBuscaPorId(), Statement.RETURN_GENERATED_KEYS)) {

            preencherId(statement, id);
            // System.out.println("SQL: " + statement.toString());

            var currentRow = statement.executeQuery();
            if (currentRow.next()) {
                t = extrairObjeto(currentRow);
            }

        } catch (SQLException ignored) {}

        return t;

    }

    @Override
    public final List<T> recuperarTodas() {

        final var entidades = new ArrayList<T>();
        final var bd = Conexao.obter();

        try (var comando = bd.prepareStatement(obterComandoRecuperarTodas(), Statement.RETURN_GENERATED_KEYS)) {

            // System.out.println("SQL: " + comando.toString());

            var linhaAtual = comando.executeQuery();
            while (linhaAtual.next()) {
                entidades.add(extrairObjeto(linhaAtual));
            }

        } catch (SQLException ignored) {}

        return entidades;

    }

    @Override
    public final boolean remover(long id) {

        final var bd = Conexao.obter();

        int resultado = 0;

        try (var comando = bd.prepareStatement(obterComandoDelecao(), Statement.RETURN_GENERATED_KEYS)) {

            preencherId(comando, id);
            resultado = comando.executeUpdate();

        } catch (SQLException ignored) {}

        return resultado == 1;

    }

}
