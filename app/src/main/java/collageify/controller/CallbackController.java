package collageify.controller;
import collageify.entity.*;
import collageify.payload.RegisterDto;
import collageify.payload.LoginDto;
import collageify.repository.UserRepository;
import collageify.repository.RoleRepository;

import collageify.security.CustomUserDetailsService;
import jakarta.servlet.http.PushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/callback")
public class CallbackController {
    @Autowired
    private UserRepository usrRepo;

}
