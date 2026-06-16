package br.com.agenda;

import br.com.agenda.model.Contato;
import br.com.agenda.service.AgendaTelefonica;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Scanner;

public class AgendaTeste {
    private static final Scanner scanner = new Scanner(System.in);
    private static AgendaTelefonica agenda;

    public static void main(String[] args) {
        ConfigurableApplicationContext contexto = new SpringApplicationBuilder(AgendaApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        agenda = contexto.getBean(AgendaTelefonica.class);

        int opcao;
        do {
            mostrarMenu();
            opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1 -> adicionarContato();
                    case 2 -> removerContato();
                    case 3 -> buscarContato();
                    case 4 -> listarContatos();
                    case 5 -> atualizarContato();
                    case 6 -> System.out.println("Saindo da agenda...");
                    default -> System.out.println("Operacao invalida. Escolha uma opcao de 1 a 6.");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } while (opcao != 6);

        contexto.close();
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== Agenda Telefonica =====");
        System.out.println("1. Adicionar contato");
        System.out.println("2. Remover contato pelo nome");
        System.out.println("3. Buscar contato pelo nome");
        System.out.println("4. Listar todos os contatos");
        System.out.println("5. Atualizar contato");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
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
        System.out.println("Contato cadastrado com sucesso.");
    }

    private static void removerContato() {
        System.out.print("Digite o nome exato do contato para remover: ");
        String nome = scanner.nextLine();

        agenda.removerContato(nome);
        System.out.println("Contato removido com sucesso.");
    }

    private static void buscarContato() {
        System.out.print("Digite o nome para buscar: ");
        String nome = scanner.nextLine();
        System.out.println(agenda.buscarContato(nome));
    }

    private static void listarContatos() {
        List<Contato> contatos = agenda.listarContatos();

        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
            return;
        }

        contatos.forEach(System.out::println);
    }

    private static void atualizarContato() {
        System.out.print("Digite o ID do contato para atualizar: ");
        Integer id = lerId();

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Novo email: ");
        String email = scanner.nextLine();

        agenda.atualizarContato(id, new Contato(nome, telefone, email));
        System.out.println("Contato atualizado com sucesso.");
    }

    private static Integer lerId() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalido. Use apenas numeros.");
            return -1;
        }
    }
}
