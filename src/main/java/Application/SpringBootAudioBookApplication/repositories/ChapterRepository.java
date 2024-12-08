package Application.SpringBootAudioBookApplication.repositories;

import Application.SpringBootAudioBookApplication.models.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findByBookVoice_BookIdAndBookVoice_VoiceActor_Id(int bookId, int voiceActorId);
}