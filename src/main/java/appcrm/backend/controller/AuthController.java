package appcrm.backend.controller;


import appcrm.backend.model.DTOs.RegisterDTO;
import appcrm.backend.model.DTOs.UserDTO;
import appcrm.backend.service.LoginService;
import appcrm.backend.service.RegisterService;
import appcrm.backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegisterService registerService;


    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody @Validated RegisterDTO registerDTO) {
        return registerService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody @Validated UserDTO userDTO) {
        var token = loginService.login(userDTO);
        return ResponseEntity.ok(token);
    }

}
