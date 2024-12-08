package Application.SpringBootAudioBookApplication.services;

import Application.SpringBootAudioBookApplication.dto.BookDescriptionDTO;
import Application.SpringBootAudioBookApplication.dto.FindBookDTO;
import Application.SpringBootAudioBookApplication.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindBooksService {

    @Autowired
    private BookRepository bookRepository;

    public List<FindBookDTO> findAllBooks(String bookname) {
        return bookRepository.findByBookNameContainingIgnoreCase(bookname).stream()
                .map(book -> new FindBookDTO(
                        book.getBookName(),
                        book.getAuthor() != null ? book.getAuthor().getAuthorName() : "Неизвестный автор",
                        book.getId()))
                .collect(Collectors.toList());
    }
    public BookDescriptionDTO findBooksDescription(int id) {
        return bookRepository.findById(id).stream()
                .map(book -> new BookDescriptionDTO(
                        book.getDescription(),
                        book.getBookImageUrl()))
                .findAny().orElse(null);
    }
}