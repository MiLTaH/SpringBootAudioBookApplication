package Application.SpringBootAudioBookApplication.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SubscribeDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    private String userName;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(max = 100, message = "Название книги должно быть до 100 символов длиной")
    private String bookName;

    public @NotEmpty(message = "Название книги не должно быть пустым") @Size(max = 100, message = "Название книги должно быть до 100 символов длиной") String getBookName() {
        return bookName;
    }

    public void setBookName(@NotEmpty(message = "Название книги не должно быть пустым") @Size(max = 100, message = "Название книги должно быть до 100 символов длиной") String bookName) {
        this.bookName = bookName;
    }

    public @NotEmpty(message = "Имя не должно быть пустым") @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной") String getUserName() {
        return userName;
    }

    public void setUserName(@NotEmpty(message = "Имя не должно быть пустым") @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной") String userName) {
        this.userName = userName;
    }
}
