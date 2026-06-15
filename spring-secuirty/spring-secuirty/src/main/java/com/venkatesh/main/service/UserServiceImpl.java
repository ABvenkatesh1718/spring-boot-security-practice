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
    // 🌐 Endpoint 1: Public Registration (Always defaults to ROLE_USER)
    public boolean registerStandardUser(String username, String password) throws UserAlreadyExistsException {
        return saveUserWithRole(username, password, Roles.ROLE_USER);
    }

    // 🔒 Endpoint 2: Admin Creation Panel (Explicitly assigns ROLE_ADMIN)
    public boolean registerAdminUser(String username, String password) throws UserAlreadyExistsException {
        return saveUserWithRole(username, password, Roles.ROLE_ADMIN);
    }

    // 🛠️ Internal Private Helper Method (Reuses your exact logic)
    private boolean saveUserWithRole(String username, String password, Roles role) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        Users user = new Users();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role); // Sets whatever role was explicitly requested by the service route

        final Users savedUser = userRepository.save(user);
        return savedUser.getId() != null;
    }
}
