package Application.SpringBootAudioBookApplication.repositories;

import Application.SpringBootAudioBookApplication.models.VoiceActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceActorRepository extends JpaRepository<VoiceActor, Integer> {
}
