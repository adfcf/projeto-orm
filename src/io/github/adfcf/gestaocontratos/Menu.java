package io.github.adfcf.gestaocontratos;

import io.github.adfcf.gestaocontratos.bd.daos.ClienteDao;
import io.github.adfcf.gestaocontratos.bd.daos.ContratoDao;
import io.github.adfcf.gestaocontratos.bd.daos.IDao;
import io.github.adfcf.gestaocontratos.entidades.Cliente;
import io.github.adfcf.gestaocontratos.entidades.Contrato;
import io.github.adfcf.gestaocontratos.entidades.Entidade;

import java.time.LocalDate;

import static io.github.adfcf.gestaocontratos.AuxiliarEntrada.*;

public final class Menu {

    public static void iniciarCiclo() {
        int opcao;
        try {
            do {

                mostrarOpcoes();
                opcao = (int) exigirLong("Escolha a opção: ");

                if (opcao == 0)
                    break;

                switch (opcao) {
                    case 1 -> cadastrarCliente();
                    case 2 -> cadastrarContrato();
                    case 3 -> editarCliente();
                    case 4 -> editarContrato();
                    case 5 -> visualizarEntidade(ClienteDao.obter());
                    case 6 -> visualizarEntidade(ContratoDao.obter());
                    case 7 -> excluirEntidade(ClienteDao.obter());
                    case 8 -> excluirEntidade(ContratoDao.obter());
                    default -> System.out.println("Opção Inválida.");
                }

                pausar();

            } while (true);
        } catch (Exception ignored) {}

        System.out.println("CONCLUIDO");

    }

    private static void mostrarOpcoes() {
        System.out.println("=== GESTÃO DE CONTRATOS ===");
        System.out.println("=== [0] Sair");
        System.out.println("=== [1] Cadastrar cliente");
        System.out.println("=== [2] Cadastrar contrato");
        System.out.println("=== [3] Editar cliente");
        System.out.println("=== [4] Editar contrato");
        System.out.println("=== [5] Visualizar cliente(s)");
        System.out.println("=== [6] Visualizar contrato(s)");
        System.out.println("=== [7] Excluir cliente(s)");
        System.out.println("=== [8] Excluir contrato(s)");
    }

    private static void cadastrarCliente() {

        System.out.println("- CADASTRO DE CLIENTE - ");

        final long cpf = exigirLong("Digite o CPF do cliente (sem algarismos): ");
        final String nome = exigirString("Digite o nome do cliente: ");

        Cliente novoCliente = new Cliente(null, cpf, nome);
        Long novoId = ClienteDao.obter().inserirOuAtualizar(novoCliente);

        if (novoId != null) {
            System.out.println("Cliente criado com sucesso. (ID = " + novoId + ")");
        } else {
            System.out.println("Falha ao criar cliente.");
        }

    }
    private static void cadastrarContrato() {

        System.out.println("- CADASTRO DE CONTRATO - ");

        final Cliente contratante = ClienteDao.obter().encontrarPorId(exigirLong("Digite o ID do contratante: "));

        if (contratante == null) {
            System.out.println("Erro: Cliente inexistente. Abortando operação...");
            return;
        }

        final String redacao = exigirString("Digite o texto do contrato: ");
        final LocalDate ultimaAlteracao = LocalDate.now();

        final Contrato novoContrato = new Contrato(
                null,
                contratante,
                redacao,
                ultimaAlteracao);
        Long novoId = ContratoDao.obter().inserirOuAtualizar(novoContrato);

        if (novoId != null) {
            System.out.println("Contrato criado com sucesso. (ID = " + novoId + ")");
        } else {
            System.out.println("Falha ao criar contrato.");
        }

    }

    private static void editarCliente() {

        System.out.println("- EDIÇÃO DE CLIENTE - ");

        final Cliente cliente = ClienteDao.obter().encontrarPorId(exigirLong("Digite o ID do cliente: "));
        if (cliente == null) {
            System.out.println("Erro: Cliente inexistente. Abortando operação...");
            return;
        }

        boolean clienteAlterado = false;

        String nome = exigirString("Digite o nome do cliente (0 para manter): ");
        if (!nome.equals("0")) {
            cliente.setNome(nome);
            clienteAlterado = true;
        }

        long cpf = exigirLong("Digite o CPF do cliente (0 para manter): ");
        if (cpf != 0L) {
            cliente.setCpf(cpf);
            clienteAlterado = true;
        }

        if (!clienteAlterado) {
            System.out.println("Nenhuma alteração feita ao cliente selecionado.");
        } else {
            if (ClienteDao.obter().inserirOuAtualizar(cliente) != null) {
                System.out.println("Cliente atualizado com sucesso.");
            } else {
                System.out.println("Falha ao alterar cliente.");
            }
        }

    }
    private static void editarContrato() {

        System.out.println("- EDIÇÃO DE CONTRATO - ");

        final var contrato = ContratoDao.obter().encontrarPorId(exigirLong("Digite o ID do contrato: "));
        if (contrato == null) {
            System.out.println("Erro: Contrato inexistente. Abortando operação...");
            return;
        }

        boolean contratoAlterado = false;

        long id = exigirLong("Digite o novo ID do contratante (0 para manter inalterado): ");
        if (id != 0L) {
            contrato.setContratante(ClienteDao.obter().encontrarPorId(id));
            contratoAlterado = true;
        }

        String novoTexto = exigirString("Digite o novo texto do contrato (0 para manter inalterado): ");
        if (!novoTexto.equals("0")) {
            contrato.setRedacao(novoTexto);
            contratoAlterado = true;
        }

        if (contratoAlterado) {
            contrato.setUltimaAtualizacao(LocalDate.now());
            ContratoDao.obter().inserirOuAtualizar(contrato);
        } else {
            System.out.println("Contrato mantido inalterado.");
        }

    }

    private static <T extends Entidade> void visualizarEntidade(IDao<T> dao) {

        System.out.println("- VISUALIZAÇÃO DE ENTIDADE - ");

        final long resposta = exigirLong("Digite o ID da entidade a ser consultada (0 para consulta completa): ");

        if (resposta != 0) {
            System.out.println(dao.encontrarPorId(resposta));
        } else {
            System.out.println(" ----- ");
            final var entidades = dao.recuperarTodas();
            for (var e : entidades) {
                System.out.println(e);
            }
            System.out.println(" ----- ");
        }

    }

    private static <T extends Entidade> void excluirEntidade(IDao<T> dao) {

        System.out.println("- EXCLUSÃO DE ENTIDADE - ");

        if (dao.remover(exigirLong("Digite o ID da entidade a ser removida: "))) {
            System.out.println("Exclusão feita com sucesso.");
        } else {
            System.out.println("Não foi possível realizar a exclusão.");
        }

    }

}
