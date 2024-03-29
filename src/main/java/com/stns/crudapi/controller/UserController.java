package com.stns.crudapi.controller;

import com.stns.crudapi.advice.AuthenticationResponse;
import com.stns.crudapi.dto.AuthRequest;
import com.stns.crudapi.dto.UserRequest;
import com.stns.crudapi.entity.User;
import com.stns.crudapi.exception.UserNotFoundException;
import com.stns.crudapi.service.JwtService;
import com.stns.crudapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/sign-up")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequest userRequest){
        return new ResponseEntity<>(service.saveUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) throws UserNotFoundException {
        return ResponseEntity.ok(service.getUser(id));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUsername());
            String message = "Authentication successful";
            AuthenticationResponse response = new AuthenticationResponse(token, message);
            return ResponseEntity.ok(response);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
