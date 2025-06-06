package Application.SpringBootAudioBookApplication.repositories;

import Application.SpringBootAudioBookApplication.models.Chapter;
import Application.SpringBootAudioBookApplication.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findByNameIn(List<String> genreNames);
}
