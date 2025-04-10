package Application.SpringBootAudioBookApplication.controllers;

import Application.SpringBootAudioBookApplication.dto.BookDescriptionDTO;
import Application.SpringBootAudioBookApplication.dto.FindBookDTO;
import Application.SpringBootAudioBookApplication.dto.FindVoicesDTO;
import Application.SpringBootAudioBookApplication.services.FindBooksService;
import Application.SpringBootAudioBookApplication.services.FindVoicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class FindBookController {

    @Autowired
    private FindBooksService findBooksService;
    @Autowired
    private FindVoicesService findVoicesService;

    @PostMapping("/show")
    public List<FindBookDTO> showBooks(@RequestParam String bookname) {
        return findBooksService.findAllBooks(bookname);
    }

    @GetMapping("/showPopularBooks")
    public List<FindBookDTO> showPopularBooks() {
        return findBooksService.findPopularBooksBooks();
    }
    @PostMapping("/showRecommendBooks")
    public List<FindBookDTO> showRecommendBooks(@RequestBody List<String> genres) {
        return findBooksService.findRecommendedBooks(genres);
    }

    @GetMapping("/{id}/voices")
    public List<FindVoicesDTO> getVoices(@PathVariable int id) {
        return findVoicesService.findVoicesByBookId(id);
    }
    @GetMapping("/{id}/description")
    public BookDescriptionDTO getDescription(@PathVariable int id) {
        return findBooksService.findBooksDescription(id);
    }
}

