package Application.SpringBootAudioBookApplication.repositories;

import Application.SpringBootAudioBookApplication.models.BookVoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookVoiceRepository extends JpaRepository<BookVoice, Long> {
}
