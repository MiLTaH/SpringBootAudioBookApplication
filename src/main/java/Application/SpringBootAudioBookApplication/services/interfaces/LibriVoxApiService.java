package Application.SpringBootAudioBookApplication.services.interfaces;

import Application.SpringBootAudioBookApplication.dto.BookDescriptionDTO;
import Application.SpringBootAudioBookApplication.dto.FindChaptersDTO;
import Application.SpringBootAudioBookApplication.dto.LibriVoxBookDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LibriVoxApiService {

    List<LibriVoxBookDTO> findBookByTitle(String title);

    List<LibriVoxBookDTO> findBookByAuthor(String author);

    List<LibriVoxBookDTO> findBooksByGenre(String genre);

    List<LibriVoxBookDTO> findBooksByLanguage(String language);

    List<FindChaptersDTO> getChaptersForBook(int librivoxBookId);

    ResponseEntity<BookDescriptionDTO> getURLImageAndDescription(int id);
}


