package Application.SpringBootAudioBookApplication.dto;

import jakarta.validation.constraints.Size;

public class BookDescriptionDTO {

    @Size(max = 511, message = "Описание книги должно быть до 511 символов длиной")
    private String description;
    @Size(max = 255, message = "Ссылка на изображение книги должна быть до 255 символов длиной")
    private String bookImageUrl;

    public BookDescriptionDTO(String description, String bookImageUrl) {
        this.description = description;
        this.bookImageUrl = bookImageUrl;
    }


    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }
    public @Size(max = 511, message = "Описание книги должно быть до 511 символов длиной") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 511, message = "Описание книги должно быть до 511 символов длиной") String description) {
        this.description = description;
    }
}
