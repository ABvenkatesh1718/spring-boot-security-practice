package com.venkatesh.main.service;

import com.venkatesh.main.entity.Roles;
import com.venkatesh.main.entity.Users;
import com.venkatesh.main.exception.UserAlreadyExistsException;
import com.venkatesh.main.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceInfo{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean addUser(String username, String password) throws UserAlreadyExistsException {

        // 1. Check if username is already taken
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("user already existed..");// Registration failed because user exists
        }

        Users user = new Users();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Roles.ROLE_USER);
        final Users savedUser = userRepository.save(user);
        return savedUser.getId() != null;
    }
}
