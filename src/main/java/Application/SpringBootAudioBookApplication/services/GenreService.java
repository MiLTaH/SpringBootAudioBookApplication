package Application.SpringBootAudioBookApplication.services;

import Application.SpringBootAudioBookApplication.dto.FindBookDTO;
import Application.SpringBootAudioBookApplication.dto.GenreDTO;
import Application.SpringBootAudioBookApplication.repositories.BookRepository;
import Application.SpringBootAudioBookApplication.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<GenreDTO> getBookGenres(String bookName) {
        return bookRepository.findByBookNameContainingIgnoreCase(bookName).stream()
                .flatMap(book -> book.getGenres().stream())
                .map(genre -> new GenreDTO(genre.getName()))
                .distinct()
                .collect(Collectors.toList());
    }
    public List<GenreDTO> getGenres() {
        return genreRepository.findAll().stream()
                .map(genre -> new GenreDTO(genre.getName()))
                .collect(Collectors.toList());
    }
}
