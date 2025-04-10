package Application.SpringBootAudioBookApplication.controllers;

import Application.SpringBootAudioBookApplication.dto.SubscribeDTO;
import Application.SpringBootAudioBookApplication.services.SubscribeUserToBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SubscribeUserToBookService subscribeUserToBookService;

    @PostMapping("/subscribeUserToBook")
    public String subscribeOrCancelUserToBook(@RequestBody SubscribeDTO subscribeDTO) {
        if (!subscribeUserToBookService.attachUserToBook(subscribeDTO.getUserName(), subscribeDTO.getBookName())) {
            subscribeUserToBookService.detachUserFromBook(subscribeDTO.getUserName(), subscribeDTO.getBookName());
            return "detached";
        } else {
            return "attached";
        }
    }
}
