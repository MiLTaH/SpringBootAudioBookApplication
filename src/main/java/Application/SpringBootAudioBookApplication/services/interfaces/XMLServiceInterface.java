package Application.SpringBootAudioBookApplication.services.interfaces;

import Application.SpringBootAudioBookApplication.dto.BookDescriptionDTO;
import Application.SpringBootAudioBookApplication.dto.LibriVoxBookDTO;

import java.util.List;
import java.util.Optional;

public interface XMLServiceInterface {

    List<LibriVoxBookDTO> findAllInLocalXmlByTitle(String title);

    List<LibriVoxBookDTO> findAllInLocalXmlByAuthor(String author);

    List<LibriVoxBookDTO> findInLocalXmlByTag(String tagName, String value);

    BookDescriptionDTO findInLocalXmlById(int id);

    void updateLocalXml();
}

