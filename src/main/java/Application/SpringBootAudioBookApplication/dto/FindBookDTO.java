package Application.SpringBootAudioBookApplication.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FindBookDTO {

    @Id
    private int id;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(max = 100, message = "Название книги должно быть до 100 символов длиной")
    private String bookName;

    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(max = 255, message = "Имя автора должно быть до 255 символов длиной")
    private String authorName;

    public FindBookDTO(String bookName, String authorName, int id) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.id = id;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}

