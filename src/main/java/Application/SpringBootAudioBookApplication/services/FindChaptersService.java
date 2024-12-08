package Application.SpringBootAudioBookApplication.services;

import Application.SpringBootAudioBookApplication.dto.FindChaptersDTO;
import Application.SpringBootAudioBookApplication.models.Chapter;
import Application.SpringBootAudioBookApplication.repositories.ChapterRepository;
import Application.SpringBootAudioBookApplication.repositories.BookVoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindChaptersService {

    private final ChapterRepository chapterRepository;
    private final BookVoiceRepository bookVoiceRepository;

    @Autowired
    public FindChaptersService(ChapterRepository chapterRepository, BookVoiceRepository bookVoiceRepository) {
        this.chapterRepository = chapterRepository;
        this.bookVoiceRepository = bookVoiceRepository;
    }

    public List<FindChaptersDTO> findChaptersByBookAndVoice(int bookId, int voiceActorId) {
        boolean bookVoiceExists = bookVoiceRepository.existsByBookIdAndVoiceActorId(bookId, voiceActorId);

        if (!bookVoiceExists) {
            throw new IllegalArgumentException("Озвучка не связана с данной книгой.");
        }

        List<Chapter> chapters = chapterRepository.findByBookVoice_BookIdAndBookVoice_VoiceActor_Id(bookId, voiceActorId);

        return chapters.stream()
                .map(chapter -> {
                    FindChaptersDTO dto = new FindChaptersDTO();
                    dto.setNameChapter(chapter.getNameChapter());
                    dto.setChapterUrl(chapter.getChapterUrl());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
