import exception.CampoVazioException;
import exception.ContatoNaoEncontradoException;
import model.Contato;
import service.AgendaTelefonica;

import java.util.List;
import java.util.Scanner;

public class AgendaTeste {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AgendaTelefonica agenda = new AgendaTelefonica();

    public static void main(String[] args) {
        exibirBoasVindas();
        int opcao = 0;

        do {
            mostrarMenu();

            try {
                opcao = lerOpcao();

                switch (opcao) {
                    case 1 -> adicionarContato();
                    case 2 -> removerContato();
                    case 3 -> buscarContato();
                    case 4 -> listarContatos();
                    case 5 -> System.out.println("Agenda finalizada. Ate mais!");
                    default -> throw new IllegalArgumentException("Escolha uma opcao entre 1 e 5.");
                }
            } catch (CampoVazioException | ContatoNaoEncontradoException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 5);

        scanner.close();
    }

    private static void exibirBoasVindas() {
        System.out.println("Bem-vindo a Agenda Telefonica!");
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== Agenda Telefonica =====");
        System.out.println("1. Adicionar contato");
        System.out.println("2. Remover contato");
        System.out.println("3. Buscar contato");
        System.out.println("4. Listar contatos");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Digite apenas numeros no menu.");
        }
    }

    private static void adicionarContato() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        agenda.adicionarContato(new Contato(nome, telefone, email));
        System.out.println("Contato adicionado com sucesso.");
    }

    private static void removerContato() {
        System.out.print("Digite o nome do contato: ");
        String nome = scanner.nextLine();

        agenda.removerContato(nome);
        System.out.println("Contato removido com sucesso.");
    }

    private static void buscarContato() {
        System.out.print("Digite o nome do contato: ");
        String nome = scanner.nextLine();

        Contato contato = agenda.buscarContato(nome);
        System.out.println("Contato encontrado:");
        System.out.println(contato);
    }

    private static void listarContatos() {
        List<Contato> contatos = agenda.listarContatos();

        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
            return;
        }

        System.out.println("Contatos cadastrados:");
        contatos.forEach(System.out::println);
    }
}
