package Application.SpringBootAudioBookApplication.services;

import Application.SpringBootAudioBookApplication.dto.BookDescriptionDTO;
import Application.SpringBootAudioBookApplication.dto.FindBookDTO;
import Application.SpringBootAudioBookApplication.dto.LibriVoxBookDTO;
import Application.SpringBootAudioBookApplication.repositories.BookRepository;
import Application.SpringBootAudioBookApplication.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  Application.SpringBootAudioBookApplication.models.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FindBooksService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private LibriVoxApiServiceImpl libriVoxApiService;

    //Локальный поиск по названию
    public List<FindBookDTO> findAllBooks(String bookname) {
        return bookRepository.findByBookNameContainingIgnoreCase(bookname).stream()
                .map(book -> new FindBookDTO(
                        book.getBookName(),
                        book.getAuthor() != null ? book.getAuthor().getAuthorName() : "Неизвестный автор",
                        book.getId()))
                .collect(Collectors.toList());
    }

    //Получение описания книги
    public BookDescriptionDTO findBooksDescription(int id) {
        return bookRepository.findById(id)
                .map(book -> new BookDescriptionDTO(
                        book.getDescription(),
                        book.getBookImageUrl()))
                .orElse(null);
    }

    //Топ 100 популярных книг
    public List<FindBookDTO> findPopularBooksBooks() {
        return bookRepository.findTop100PopularBooksNative().stream()
                .map(book -> new FindBookDTO(
                        book.getBookName(),
                        book.getAuthor() != null ? book.getAuthor().getAuthorName() : "Неизвестный автор",
                        book.getId()))
                .collect(Collectors.toList());
    }

    //Рекомендованные книги по жанрам
    public List<FindBookDTO> findRecommendedBooks(List<String> genreNames) {
        List<Integer> genreIds = genreRepository.findByNameIn(genreNames).stream()
                .map(Genre::getId)
                .toList();
        return bookRepository.findAllBooksSortedByGenreMatchesAndUserCount(genreIds).stream()
                .map(book -> new FindBookDTO(
                        book.getBookName(),
                        book.getAuthor() != null ? book.getAuthor().getAuthorName() : "Неизвестный автор",
                        book.getId()))
                .collect(Collectors.toList());
    }
}
