package io.github.adfcf.gestaocontratos;

import io.github.adfcf.gestaocontratos.bd.Conexao;

public final class Main {
    public static void main(String[] args) {
        System.out.println("Conectando-se ao banco de dados...\n");
        Conexao.obter();
        Menu.iniciarCiclo();
    }
}
