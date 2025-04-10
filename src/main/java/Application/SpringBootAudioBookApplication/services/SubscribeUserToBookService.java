package Application.SpringBootAudioBookApplication.services;

import Application.SpringBootAudioBookApplication.models.Book;
import Application.SpringBootAudioBookApplication.models.User;
import Application.SpringBootAudioBookApplication.repositories.BookRepository;
import Application.SpringBootAudioBookApplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SubscribeUserToBookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean attachUserToBook(String userName, String bookName) {
        Book book = bookRepository.findByBookName(bookName)
                .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        if (!book.getUsers().contains(user)) {
            book.getUsers().add(user);
            bookRepository.save(book);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void detachUserFromBook(String userName, String bookName) {
        Book book = bookRepository.findByBookName(bookName)
                .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        if (book.getUsers().contains(user)) {
            book.getUsers().remove(user);
            bookRepository.save(book);
        }
    }
}
