package io.github.adfcf.gestaocontratos.entidades;

public abstract class Entidade {

    protected Long id;

    protected Entidade(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[" + id + "]";
    }

}
