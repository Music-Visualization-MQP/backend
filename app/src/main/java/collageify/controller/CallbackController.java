package collageify.controller;
import collageify.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/callback")
public class CallbackController {
    @Autowired
    private UserRepository usrRepo;

}
