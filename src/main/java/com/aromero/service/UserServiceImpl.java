package com.aromero.service;

import com.aromero.entity.User;
import com.aromero.exception.UsernameAlreadyExistsException;
import com.aromero.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setConfirmPassword(null);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("User name '" + user.getUsername() + "' already exists.");
        }
    }
}
