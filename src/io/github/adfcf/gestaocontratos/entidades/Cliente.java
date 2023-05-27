package io.github.adfcf.gestaocontratos.entidades;

public class Cliente extends Entidade {

    public static final int NOME_MAX = 45;

    private Long cpf;
    private String nome;

    public Cliente(Long id, Long cpf, String nome) {

        super(id);

        setCpf(cpf);
        setNome(nome);

    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome.length() >= NOME_MAX) {
            throw new IllegalArgumentException("Nome deve ser menor que " + NOME_MAX + " caracteres.");
        }
        this.nome = nome;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + getNome() + " - " + getCpf();
    }

}
