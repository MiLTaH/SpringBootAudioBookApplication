package Application.SpringBootAudioBookApplication.repositories;

import Application.SpringBootAudioBookApplication.models.BookVoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookVoiceRepository extends JpaRepository<BookVoice, Integer> {
    List<BookVoice> findByBookId(int bookId);
    boolean existsByBookIdAndVoiceActorId(int bookId, int voiceActorId);
}

