package io.github.adfcf.gestaocontratos.entidades;

import java.time.LocalDate;

public class Contrato extends Entidade {

    public static final int REDACAO_MAX = 100_000;

    private Cliente contratante;
    private String redacao;
    private LocalDate ultimaAtualizacao;

    public Contrato(Long id, Cliente contratante, String redacao, LocalDate ultimaAtualizacao) {

        super(id);

        setRedacao(redacao);
        setUltimaAtualizacao(ultimaAtualizacao);
        setContratante(contratante);

    }

    public String getRedacao() {
        return redacao;
    }

    public void setRedacao(String redacao) {

        if (redacao.length() > REDACAO_MAX) {
            throw new IllegalArgumentException("Redação de contrato não deve possuir mais que " + REDACAO_MAX + " caracteres.");
        }

        this.redacao = redacao;

    }

    public LocalDate getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Cliente getContratante() {
        return contratante;
    }

    public void setContratante(Cliente contratante) {
        this.contratante = contratante;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + getRedacao() + " - " + contratante.getNome();
    }

}
