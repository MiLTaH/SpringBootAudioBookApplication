package Application.SpringBootAudioBookApplication.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "BookVoices")
public class BookVoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_Book", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "id_VoiceActors", referencedColumnName = "id")
    private VoiceActor voiceActor;

    @OneToMany(mappedBy = "bookVoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters;

    // Конструкторы, геттеры и сеттеры
    public BookVoice() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public VoiceActor getVoiceActor() {
        return voiceActor;
    }

    public void setVoiceActor(VoiceActor voiceActor) {
        this.voiceActor = voiceActor;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}