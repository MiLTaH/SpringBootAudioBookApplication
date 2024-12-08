package Application.SpringBootAudioBookApplication.services;

import Application.SpringBootAudioBookApplication.dto.FindVoicesDTO;
import Application.SpringBootAudioBookApplication.repositories.BookVoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindVoicesService {

    private final BookVoiceRepository bookVoiceRepository;

    @Autowired
    public FindVoicesService(BookVoiceRepository bookVoiceRepository) {
        this.bookVoiceRepository = bookVoiceRepository;
    }

    public List<FindVoicesDTO> findVoicesByBookId(int bookId) {
        return bookVoiceRepository.findByBookId(bookId).stream()
                .map(bookVoice -> new FindVoicesDTO(
                        bookVoice.getVoiceActor().getId(),
                        bookVoice.getVoiceActor().getNameActor()))
                .collect(Collectors.toList());
    }
}


