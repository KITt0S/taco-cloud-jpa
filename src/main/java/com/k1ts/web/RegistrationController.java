package com.k1ts.web;

import com.k1ts.RegistrationForm;
import com.k1ts.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping( "/register" )
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController( UserRepository userRepo, PasswordEncoder passwordEncoder ) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {

        return "registration";
    }

    @PostMapping
    public String processRegistration( RegistrationForm form ) {

        userRepo.save( form.toUser( passwordEncoder ) );
        return "redirect:/login";
    }
}
