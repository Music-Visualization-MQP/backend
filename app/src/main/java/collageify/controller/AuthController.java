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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/pee")
    public ResponseEntity<String> pee(){
        return new ResponseEntity<>("hey there this works", HttpStatus.OK);
    }

    /**
     *
     * @param loginDto login dto is a simple class that holds a username/email and the password of a user aattemting...
     *                 to authenticate
     * @return should return http response stating that the user has been found and that they are now authenticated
     *
     * The problem that i see is that the
     */
    @PostMapping({"/signin", "/login"})
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        //remove this
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword());
        Authentication auth = authMgr.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ResponseEntity<>("user signed in ur welcme." + token , HttpStatus.OK);
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
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepo.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        usrRepo.save(user);

        return new ResponseEntity<>("urinethere", HttpStatus.OK);

    }

}
