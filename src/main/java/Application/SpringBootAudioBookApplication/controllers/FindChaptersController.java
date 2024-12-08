package Application.SpringBootAudioBookApplication.controllers;

import Application.SpringBootAudioBookApplication.dto.FindChaptersDTO;
import Application.SpringBootAudioBookApplication.services.FindChaptersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapters")
public class FindChaptersController {

    private FindChaptersService findChaptersService;
    @Autowired
    public FindChaptersController(FindChaptersService findChaptersService) {
        this.findChaptersService = findChaptersService;
    }

    @GetMapping("/{bookId}/{voiceActorId}")
    public List<FindChaptersDTO> getChaptersByBookAndVoice(
            @PathVariable int bookId,
            @PathVariable int voiceActorId) {
        return findChaptersService.findChaptersByBookAndVoice(bookId, voiceActorId);
    }
}

