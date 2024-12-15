package Application.SpringBootAudioBookApplication.services;

import Application.SpringBootAudioBookApplication.models.User;
import Application.SpringBootAudioBookApplication.repositories.UserRepository;
import Application.SpringBootAudioBookApplication.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> person = userRepository.findByUsername(s);
        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new UserDetails(person.get());
    }
}