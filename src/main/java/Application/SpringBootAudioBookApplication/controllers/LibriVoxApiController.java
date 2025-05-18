package Application.SpringBootAudioBookApplication.controllers;

import Application.SpringBootAudioBookApplication.dto.BookDescriptionDTO;
import Application.SpringBootAudioBookApplication.dto.FindChaptersDTO;
import Application.SpringBootAudioBookApplication.dto.LibriVoxBookDTO;
import Application.SpringBootAudioBookApplication.services.interfaces.LibriVoxApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/librivox")
public class LibriVoxApiController {

    @Autowired
    private LibriVoxApiService libriVoxApiService;

    public LibriVoxApiController(LibriVoxApiService libriVoxApiService) {
        this.libriVoxApiService = libriVoxApiService;
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<LibriVoxBookDTO>> findBooksByTitle(@RequestParam String title) {
        List<LibriVoxBookDTO> result = libriVoxApiService.findBookByTitle(title);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/author")
    public ResponseEntity<List<LibriVoxBookDTO>> findBooksByAuthor(@RequestParam String author) {
        List<LibriVoxBookDTO> result = libriVoxApiService.findBookByAuthor(author);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/language")
    public ResponseEntity<List<LibriVoxBookDTO>> findBooksByLanguage(@RequestParam String language) {
        List<LibriVoxBookDTO> results = libriVoxApiService.findBooksByLanguage(language);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search/genre")
    public ResponseEntity<List<LibriVoxBookDTO>> findBooksByGenre(@RequestParam String genre) {
        List<LibriVoxBookDTO> results = libriVoxApiService.findBooksByGenre(genre);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}/chapters")
    public List<FindChaptersDTO> getChaptersForBook(@PathVariable("id") int id) {
        return libriVoxApiService.getChaptersForBook(id);
    }
    @GetMapping("book/description/{id}")
    public ResponseEntity<BookDescriptionDTO> getBookDescription(@PathVariable("id") int id) {
        return libriVoxApiService.getURLImageAndDescription(id);
    }
}

