package appcrm.backend.service;


import appcrm.backend.model.DTOs.UserDTO;
import appcrm.backend.model.User;
import appcrm.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public String login (UserDTO userDTO){
        UserDetails user = userRepository.findByLogin(userDTO.login());
        if (user != null) {
            try {
                var usernamePassword = new UsernamePasswordAuthenticationToken(userDTO.login(), userDTO.password());
                var auth = authenticationManager.authenticate(usernamePassword);
                var token = tokenService.generateToken((User) auth.getPrincipal());

                return token;
            } catch (Exception e) {
                throw new RuntimeException("Invalid password.");
            }
        } else {
            throw new RuntimeException("Login not found");
        }

    }

}
