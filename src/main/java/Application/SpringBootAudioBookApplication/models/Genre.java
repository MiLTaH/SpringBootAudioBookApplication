package Application.SpringBootAudioBookApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Жанр не должен быть пустым")
    @Size(max = 100, message = "Жанр должен быть до 100 символов длиной")
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Book> books;

    public Genre() {
    }

    public @NotEmpty(message = "Жанр не должен быть пустым") @Size(max = 100, message = "Жанр должен быть до 100 символов длиной") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Жанр не должен быть пустым") @Size(max = 100, message = "Жанр должен быть до 100 символов длиной") String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
