package Application.SpringBootAudioBookApplication.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FindChaptersDTO {

    @NotEmpty(message = "Название главы не должно быть пустым")
    @Size(max = 100, message = "Название главы должно быть до 100 символов длиной")
    private String nameChapter;

    @Size(max = 255, message = "Ссылка на главу должна быть до 255 символов длиной")
    @Column(name = "chapterurl")
    private String chapterUrl;

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
}
