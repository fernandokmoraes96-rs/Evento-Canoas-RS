package services;

import models.*;
import java.io.*;
import java.util.*;
import java.time.LocalDateTime;

public class EventService {
    private List<User> users = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
    private final String FILE_NAME = "events.data";

    public EventService() {
        load();
    }

    private void load() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                // formatação simples: id;nome;categoria;data;endereco;descricao
                String[] parts = line.split(";");
                if (parts.length >= 6) {
                    Event e = new Event(
                        parts[0],
                        parts[1],
                        parts[2],
                        Category.valueOf(parts[3]),
                        LocalDateTime.parse(parts[4]),
                        parts[5]
                    );
                    events.add(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro lendo arquivo: " + e.getMessage());
        }
    }

    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Event e : events) {
                pw.println(String.join(";", Arrays.asList(
                    e.getId(),
                    e.getNome(),
                    e.getEndereco(),
                    e.getCategoria().name(),
                    e.getHorario().toString(),
                    e.getDescricao()
                )));
            }
        } catch (Exception e) {
            System.out.println("Erro salvando arquivo: " + e.getMessage());
        }
    }

    public User addUser(String nome, String email) {
        User u = new User(UUID.randomUUID().toString(), nome, email);
        users.add(u);
        return u;
    }

    public Event addEvent(String nome, String endereco, Category categoria, LocalDateTime horario, String descricao) {
        Event ev = new Event(UUID.randomUUID().toString(), nome, endereco, categoria, horario, descricao);
        events.add(ev);
        save();
        return ev;
    }

    public List<Event> listEvents() {
        events.sort(Comparator.comparing(Event::getHorario));
        return events;
    }

    public List<Event> happeningNow() {
        List<Event> res = new ArrayList<>();
        for (Event e : events) {
            if (e.isAgora()) res.add(e);
        }
        return res;
    }

    public List<Event> pastEvents() {
        List<Event> res = new ArrayList<>();
        for (Event e : events) {
            if (e.isPassado()) res.add(e);
        }
        return res;
    }

    public void confirm(String eventId, String userId) {
        for (Event e : events) {
            if (e.getId().equals(eventId)) {
                e.confirmar(userId);
                save();
            }
        }
    }
}
