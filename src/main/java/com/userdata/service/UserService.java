package com.userdata.service;

import org.springframework.stereotype.Service;

import com.userdata.entity.Users;
import com.userdata.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UsersRepository userRepository;

    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> allUsers() {
        List<Users> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
