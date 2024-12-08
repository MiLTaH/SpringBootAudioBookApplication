package Application.SpringBootAudioBookApplication.repositories;

import Application.SpringBootAudioBookApplication.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookNameContainingIgnoreCase(String bookname);
}
