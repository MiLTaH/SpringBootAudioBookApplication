package Application.SpringBootAudioBookApplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibriVoxBookDTO {
    private int id;
    private String bookName;
    private String authorName;
    private String urlLibriVox;
    private String genre;
    private String urlImage;
    private String language;
    private String description;
}

