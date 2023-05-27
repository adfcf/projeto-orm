package io.github.adfcf.gestaocontratos;

import java.util.Scanner;

public final class AuxiliarEntrada {

    public static String exigirString(String mensagem) {
        Scanner s = new Scanner(System.in);
        boolean respostaInvalida = true;
        String resposta = null;
        do {
            try {
                System.out.println(mensagem);
                resposta = s.nextLine();
                s.reset();
            } catch (Exception ignored) {}
            if (resposta != null) {
                respostaInvalida = false;
            } else {
                System.out.println("Erro. Tente novamente.");
            }
        } while (respostaInvalida);
        return resposta;
    }

    public static long exigirLong(String mensagem) {
        Scanner s = new Scanner(System.in);
        boolean respostaInvalida;
        long resposta = 0;
        do {
            try {
                System.out.println(mensagem);
                resposta = s.nextLong();
                s.reset();
                respostaInvalida = false;
            } catch (Exception ignored) {
                respostaInvalida = true;
                System.out.println("Erro. Tente novamente.");
            }
        } while (respostaInvalida);
        return resposta;
    }

    public static void pausar() {
        Scanner s = new Scanner(System.in);
        System.out.println("Insira qualquer valor para continuar...");
        s.next();
        s.reset();
    }

}
