package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String id;
    private String nome;
    private String endereco;
    private Category categoria;
    private LocalDateTime horario;
    private String descricao;
    private List<String> confirmados = new ArrayList<>();

    public Event(String id, String nome, String endereco, Category categoria, LocalDateTime horario, String descricao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public Category getCategoria() { return categoria; }
    public LocalDateTime getHorario() { return horario; }
    public String getDescricao() { return descricao; }
    public List<String> getConfirmados() { return confirmados; }

    public boolean isPassado() {
        return horario.isBefore(LocalDateTime.now());
    }

    public boolean isAgora() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(horario) && now.isBefore(horario.plusHours(2));
    }

    public void confirmar(String userId) {
        if (!confirmados.contains(userId)) {
            confirmados.add(userId);
        }
    }

    @Override
    public String toString() {
        return "Evento: " + nome + " | " + categoria + " | " + horario + " | " + endereco + "\n" +
               descricao + "\nConfirmados: " + confirmados.size();
    }
}
