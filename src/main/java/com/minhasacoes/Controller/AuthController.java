package com.minhasacoes.Controller;

import com.minhasacoes.Config.Security.JWTUtil;
import com.minhasacoes.Entities.Person;
import com.minhasacoes.Model.LoginCredentials;
import com.minhasacoes.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody Person person) {
        String encodedPass = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPass);

        person = personRepository.save(person);

        String token = jwtUtil.generateToken(person.getPassword());

        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials loginCredentials) {
        try {
            UsernamePasswordAuthenticationToken authTokenInput =
                    new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword());

            authenticationManager.authenticate(authTokenInput);

            String token = jwtUtil.generateToken(loginCredentials.getUsername());

            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

}
