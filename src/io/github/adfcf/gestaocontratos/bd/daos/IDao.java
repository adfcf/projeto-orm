package io.github.adfcf.gestaocontratos.bd.daos;

import io.github.adfcf.gestaocontratos.entidades.Entidade;

import java.util.List;

public interface IDao<T extends Entidade> {

    Long inserirOuAtualizar(T t);

    T encontrarPorId(long id);

    List<T> recuperarTodas();

    boolean remover(long id);

}
