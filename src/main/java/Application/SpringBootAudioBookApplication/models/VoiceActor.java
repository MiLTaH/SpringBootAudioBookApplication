package Application.SpringBootAudioBookApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "VoiceActors")
public class VoiceActor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Имя актера не должно быть пустым")
    @Size(max = 100, message = "Имя актера должно быть до 100 символов длиной")
    @Column(name = "nameactor")
    private String nameActor;

    @Size(max = 1023, message = "Ссылка на актерские работы должна быть до 1023 символов длиной")
    @Column(name = "referenses")
    private String references;

    @OneToMany(mappedBy = "voiceActor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookVoice> bookVoices;

    // Конструкторы, геттеры и сеттеры
    public VoiceActor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameActor() {
        return nameActor;
    }

    public void setNameActor(String nameActor) {
        this.nameActor = nameActor;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public List<BookVoice> getBookVoices() {
        return bookVoices;
    }

    public void setBookVoices(List<BookVoice> bookVoices) {
        this.bookVoices = bookVoices;
    }
}