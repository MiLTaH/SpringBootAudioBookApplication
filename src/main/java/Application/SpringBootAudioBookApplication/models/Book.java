package Application.SpringBootAudioBookApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(max = 100, message = "Название книги должно быть до 100 символов длиной")
    @Column(name = "bookname")
    private String bookName;

    @Size(max = 511, message = "Описание книги должно быть до 511 символов длиной")
    @Column(name = "description")
    private String description;

    @Size(max = 255, message = "Ссылка на изображение книги должна быть до 255 символов длиной")
    @Column(name = "bookimageurl")
    private String bookImageUrl;

    @ManyToOne
    @JoinColumn(name = "id_readuser", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_author", referencedColumnName = "id")
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookVoice> bookVoices;

    // Конструкторы, геттеры и сеттеры
    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<BookVoice> getBookVoices() {
        return bookVoices;
    }

    public void setBookVoices(List<BookVoice> bookVoices) {
        this.bookVoices = bookVoices;
    }
}

