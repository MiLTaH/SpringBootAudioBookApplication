package Application.SpringBootAudioBookApplication.controllers;

import Application.SpringBootAudioBookApplication.dto.AuthenticationUserDTO;
import Application.SpringBootAudioBookApplication.dto.RegistrationUserDTO;
import Application.SpringBootAudioBookApplication.models.User;
import Application.SpringBootAudioBookApplication.security.JWTUtil;
import Application.SpringBootAudioBookApplication.services.RegistrationService;
import Application.SpringBootAudioBookApplication.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final UserValidator userValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(RegistrationService registrationService, UserValidator userValidator,
                          JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid RegistrationUserDTO registrationUserDTO,
                                                   BindingResult bindingResult) {
        logger.info("Received registrationUserDTO: {}", registrationUserDTO);
        User user = convertToPerson(registrationUserDTO);
        if (bindingResult.hasErrors()) {
            logger.warn("Validation failed: {}", bindingResult.getAllErrors());
        }

        userValidator.validate(user, bindingResult);
        System.out.println(registrationUserDTO.toString());
        if (bindingResult.hasErrors()) {
            return Map.of("message", "Ошибка!");
        }

        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationUserDTO authenticationUserDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationUserDTO.getUsername(),
                        authenticationUserDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationUserDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    public User convertToPerson(RegistrationUserDTO registrationUserDTO) {
        return this.modelMapper.map(registrationUserDTO, User.class);
    }
}
