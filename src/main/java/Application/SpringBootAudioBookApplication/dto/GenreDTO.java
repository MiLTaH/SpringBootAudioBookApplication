package Application.SpringBootAudioBookApplication.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class GenreDTO {

    @NotEmpty(message = "Жанр не должен быть пустым")
    @Size(max = 100, message = "Жанр должен быть до 100 символов длиной")
    private String genre;

    public GenreDTO() {
    }
    public GenreDTO(String genre) {
        this.genre = genre;
    }

    public @NotEmpty(message = "Жанр не должен быть пустым") @Size(max = 100, message = "Жанр должен быть до 100 символов длиной") String getGenre() {
        return genre;
    }

    public void setGenre(@NotEmpty(message = "Жанр не должен быть пустым") @Size(max = 100, message = "Жанр должен быть до 100 символов длиной") String genre) {
        this.genre = genre;
    }
}
