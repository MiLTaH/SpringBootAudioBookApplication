package Application.SpringBootAudioBookApplication.services;

import Application.SpringBootAudioBookApplication.dto.BookDescriptionDTO;
import Application.SpringBootAudioBookApplication.dto.FindChaptersDTO;
import Application.SpringBootAudioBookApplication.dto.LibriVoxBookDTO;
import Application.SpringBootAudioBookApplication.models.Book;
import Application.SpringBootAudioBookApplication.repositories.BookRepository;
import Application.SpringBootAudioBookApplication.services.interfaces.LibriVoxApiService;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibriVoxApiServiceImpl implements LibriVoxApiService {

    private static final String LOCAL_XML_PATH = "data/librivox.xml";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private XMLService xmlService;

    @Override
    public List<LibriVoxBookDTO> findBookByTitle(String title) {
        List<LibriVoxBookDTO> results = new ArrayList<>();
        // 1. Поиск в БД
        List<Book> books = bookRepository.findAllByBookNameIgnoreCase(title);
        for (Book book : books) {
            results.add(mapToDto(book));
        }
        // 2. Поиск в локальном XML
        List<LibriVoxBookDTO> fromXml = xmlService.findAllInLocalXmlByTitle(title);
        results.addAll(fromXml);
        // 3. Обновление XML и повторный поиск
        if (results.isEmpty()) {
            xmlService.updateLocalXml();
            results.addAll(xmlService.findAllInLocalXmlByTitle(title));
        }
        return results;
    }

    @Override
    public List<LibriVoxBookDTO> findBookByAuthor(String author) {
        List<LibriVoxBookDTO> results = new ArrayList<>();
        List<Book> books = bookRepository.findByAuthor_AuthorNameIgnoreCase(author);
        for (Book book : books) {
            results.add(mapToDto(book));
        }
        List<LibriVoxBookDTO> fromXml = xmlService.findAllInLocalXmlByAuthor(author);
        results.addAll(fromXml);
        if (results.isEmpty()) {
            xmlService.updateLocalXml();
            results.addAll(xmlService.findAllInLocalXmlByAuthor(author));
        }
        return results;
    }


    @Override
    public List<LibriVoxBookDTO> findBooksByGenre(String genre) {
        List<Book> books = bookRepository.findByGenres_NameIgnoreCase(genre);
        if (!books.isEmpty()) {
            return books.stream().map(this::mapToDto).toList();
        }
        List<LibriVoxBookDTO> fromXml = xmlService.findInLocalXmlByTag("genre", genre);
        if (!fromXml.isEmpty()) return fromXml;
        xmlService.updateLocalXml();
        return xmlService.findInLocalXmlByTag("genre", genre);
    }

    @Override
    public List<FindChaptersDTO> getChaptersForBook(int librivoxBookId) {
        List<FindChaptersDTO> chapters = new ArrayList<>();
        String rssUrl = "https://librivox.org/rss/" + librivoxBookId;
        try {
            URL url = new URL(rssUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));
            for (SyndEntry entry : feed.getEntries()) {
                String title = entry.getTitle();
                String audioUrl = null;
                if (!entry.getEnclosures().isEmpty()) {
                    SyndEnclosure enclosure = entry.getEnclosures().get(0);
                    audioUrl = enclosure.getUrl();
                }
                if (title != null && audioUrl != null) {
                    FindChaptersDTO chapter = new FindChaptersDTO();
                    chapter.setNameChapter(title);
                    chapter.setChapterUrl(audioUrl);
                    chapters.add(chapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chapters;
    }

    @Override
    public List<LibriVoxBookDTO> findBooksByLanguage(String language) {
        List<LibriVoxBookDTO> fromXml = xmlService.findInLocalXmlByTag("language", language);
        if (!fromXml.isEmpty()) return fromXml;
        xmlService.updateLocalXml();
        return xmlService.findInLocalXmlByTag("language", language);
    }
    @Override
    public ResponseEntity<BookDescriptionDTO> getURLImageAndDescription(int id) {
        BookDescriptionDTO fromDb = bookRepository.findById(id)
                .map(book -> new BookDescriptionDTO(book.getDescription(), book.getBookImageUrl()))
                .orElse(null);
        if (fromDb != null) return ResponseEntity.ok(fromDb);
        BookDescriptionDTO fromXml = xmlService.findInLocalXmlById(id);
        if (fromXml != null) return ResponseEntity.ok(fromXml);
        xmlService.updateLocalXml();
        BookDescriptionDTO fromUpdatedXml = xmlService.findInLocalXmlById(id);
        if (fromUpdatedXml != null) return ResponseEntity.ok(fromUpdatedXml);
        return ResponseEntity.notFound().build();
    }

    private LibriVoxBookDTO mapToDto(Book book) {
        LibriVoxBookDTO dto = new LibriVoxBookDTO();
        dto.setId(book.getId());
        dto.setBookName(book.getBookName());
        dto.setAuthorName(book.getAuthor().getAuthorName());
        dto.setLanguage("Unknown");
        dto.setUrlLibriVox(null);
        return dto;
    }
}
