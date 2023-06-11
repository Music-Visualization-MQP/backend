package collageify.controller;

import collageify.entity.*;
import collageify.payload.RegisterDto;
import collageify.payload.LoginDto;
import collageify.repository.UserRepository;
import collageify.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authMgr;

    @Autowired
    private UserRepository usrRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication auth = authMgr.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ResponseEntity<>("user signed in ur welcme.", HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        if(usrRepo.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("username is already taken", HttpStatus.BAD_REQUEST);

        }
        if(usrRepo.existsByEmail(registerDto.getEmail())){
            return new ResponseEntity<>("email is taken try sumn else bozo", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepo.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        usrRepo.save(user);

        return new ResponseEntity<>("urinethere", HttpStatus.OK);

    }
}
