package Application.SpringBootAudioBookApplication.controllers;

import Application.SpringBootAudioBookApplication.dto.GenreDTO;
import Application.SpringBootAudioBookApplication.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @PostMapping("/getBookGenre")
    public List<GenreDTO> getBookGenre(@RequestBody Map<String, String> request) {
        String bookName = request.get("bookName");
        return genreService.getBookGenres(bookName);
    }
    @GetMapping("/getGenres")
    public List<GenreDTO> getGenres() {
        return genreService.getGenres();
    }
}
