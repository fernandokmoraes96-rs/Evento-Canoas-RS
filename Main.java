import models.*;
import services.EventService;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final EventService svc = new EventService();
    private static User currentUser;

    public static void main(String[] args) {
        System.out.println("=== Sistema de Eventos ===");

        login();

        int op;
        do {
            menu();
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> cadastrarEvento();
                case 2 -> listarEventos();
                case 3 -> confirmarPresenca();
                case 4 -> verEventosAgora();
                case 5 -> verEventosPassados();
            }
        } while (op != 0);

        System.out.println("Encerrando...");
    }

    private static void login() {
        System.out.print("Digite seu nome: ");
        String nome = sc.nextLine();
        System.out.print("Digite seu email: ");
        String email = sc.nextLine();

        currentUser = svc.addUser(nome, email);
    }

    private static void menu() {
        System.out.println("\n1 - Cadastrar evento");
        System.out.println("2 - Listar eventos");
        System.out.println("3 - Confirmar presença");
        System.out.println("4 - Ver eventos acontecendo agora");
        System.out.println("5 - Ver eventos passados");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private static void cadastrarEvento() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        System.out.println("Categorias: FESTA, ESPORTIVO, SHOW, CULTURAL, TECNOLOGIA, OUTROS");
        String catStr = sc.nextLine().toUpperCase();
        Category cat = Category.valueOf(catStr);

        System.out.print("Data/hora (yyyy-MM-ddTHH:mm): ");
        String data = sc.nextLine();
        LocalDateTime dt = LocalDateTime.parse(data);

        System.out.print("Descrição: ");
        String desc = sc.nextLine();

        svc.addEvent(nome, endereco, cat, dt, desc);
    }

    private static void listarEventos() {
        for (Event e : svc.listEvents()) {
            System.out.println(e);
            System.out.println("ID: " + e.getId());
            System.out.println("-----------------------");
        }
    }

    private static void confirmarPresenca() {
        System.out.print("Digite ID do evento: ");
        String id = sc.nextLine();
        svc.confirm(id, currentUser.getId());
    }

    private static void verEventosAgora() {
        for (Event e : svc.happeningNow()) {
            System.out.println(e);
        }
    }

    private static void verEventosPassados() {
        for (Event e : svc.pastEvents()) {
            System.out.println(e);
        }
    }
}
