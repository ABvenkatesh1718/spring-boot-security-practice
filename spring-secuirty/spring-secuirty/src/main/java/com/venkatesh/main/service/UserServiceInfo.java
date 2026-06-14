package com.venkatesh.main.service;

import com.venkatesh.main.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public interface UserServiceInfo {
    public boolean addUser(String username,String password) throws UserAlreadyExistsException;
}
