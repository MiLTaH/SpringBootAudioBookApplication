package Application.SpringBootAudioBookApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Название главы не должно быть пустым")
    @Size(max = 100, message = "Название главы должно быть до 100 символов длиной")
    @Column(name = "namechapter")
    private String nameChapter;

    @Size(max = 255, message = "Ссылка на главу должна быть до 255 символов длиной")
    @Column(name = "chapterURL")
    private String chapterUrl;

    @ManyToOne
    @JoinColumn(name = "id_voice", referencedColumnName = "id")
    private BookVoice bookVoice;

    @Size(max = 255, message = "Описание главы должно быть до 255 символов длиной")
    @Column(name = "description")
    private String description;

    // Конструкторы, геттеры и сеттеры
    public Chapter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameChapter() {
        return nameChapter;
    }

    public void setNameChapter(String nameChapter) {
        this.nameChapter = nameChapter;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public BookVoice getBookVoice() {
        return bookVoice;
    }

    public void setBookVoice(BookVoice bookVoice) {
        this.bookVoice = bookVoice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}