package Application.SpringBootAudioBookApplication.repositories;

import Application.SpringBootAudioBookApplication.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findById(int id);

    List<Book> findByBookNameContainingIgnoreCase(String bookname);

    Optional<Book> findByBookName(String bookName);

    List<Book> findByAuthor_AuthorNameIgnoreCase(String authorName);

    List<Book> findByGenres_NameIgnoreCase(String genreName);

    @Query(
            value = "SELECT b.* FROM books b " +
                    "LEFT JOIN book_user bu ON b.id = bu.book_id " +
                    "GROUP BY b.id " +
                    "ORDER BY COUNT(bu.user_id) DESC " +
                    "LIMIT 100",
            nativeQuery = true
    )
    List<Book> findTop100PopularBooksNative();

    @Query(value = """
    SELECT sub.*
    FROM (
        SELECT b.*, 
               COUNT(DISTINCT CASE WHEN bg.genre_id IN (:genreIds) THEN bg.genre_id END) AS matching_genres_count,
               COUNT(DISTINCT bu.user_id) AS user_count
        FROM books b
        LEFT JOIN book_genre bg ON b.id = bg.book_id
        LEFT JOIN book_user bu ON b.id = bu.book_id
        GROUP BY b.id
    ) AS sub
    ORDER BY 
        (COALESCE(sub.matching_genres_count, 0) * 1000 + COALESCE(sub.user_count, 0)) DESC
    LIMIT 100
    """, nativeQuery = true)
    List<Book> findAllBooksSortedByGenreMatchesAndUserCount(@Param("genreIds") List<Integer> genreIds);

    List<Book> findAllByBookNameIgnoreCase(String title);
}