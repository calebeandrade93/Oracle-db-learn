package com.oracle.dblearn.service;

import com.oracle.dblearn.exception.EmailAlreadyExistsException;
import com.oracle.dblearn.exception.InvalidPasswordException;
import com.oracle.dblearn.model.entity.User;
import com.oracle.dblearn.repository.UserRepository;
import com.oracle.dblearn.infra.utils.PasswordChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("E-mail already exists");
        }
        PasswordChecker passwordChecker = new PasswordChecker(user.getPassword());
        if (!passwordChecker.isValid()) {
            throw new InvalidPasswordException(passwordChecker.getMessage().toString());
        }

        return userRepository.save(user);
    }



}
