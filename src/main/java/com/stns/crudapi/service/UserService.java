package com.stns.crudapi.service;

import com.stns.crudapi.dto.UserRequest;
import com.stns.crudapi.entity.User;
import com.stns.crudapi.exception.UserNotFoundException;
import com.stns.crudapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserRequest userRequest){
        User user = User.build(0, userRequest.getName(), userRequest.getEmail(),
                userRequest.getPassword(), userRequest.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(int id) throws UserNotFoundException {
        User user = userRepository.findById(id);
        if(user != null){
            return user;
        }else{
            throw new UserNotFoundException("user not found with id: " + id);
        }
    }
}
