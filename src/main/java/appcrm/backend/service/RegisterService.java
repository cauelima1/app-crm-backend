package appcrm.backend.service;

import appcrm.backend.model.DTOs.RegisterDTO;
import appcrm.backend.model.User;
import appcrm.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<String> register(RegisterDTO registerDTO) {
        if (userRepository.findByLogin(registerDTO.login()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User exists.");
        }

        if (registerDTO.password().length() < 4) {
            return ResponseEntity.badRequest().body("Verify password, must be more than 4 digits.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.login(), encryptedPassword);
        newUser.setCreatedAt(OffsetDateTime.now());
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User " + newUser.getLogin() + " \nCreated at: " + newUser.getCreatedAt().toLocalDateTime());
    }


}
