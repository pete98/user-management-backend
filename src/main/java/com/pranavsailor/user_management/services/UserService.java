package com.pranavsailor.user_management.services;

import com.pranavsailor.user_management.dtos.RegisterUserDto;
import com.pranavsailor.user_management.entities.Role;
import com.pranavsailor.user_management.entities.RoleEnum;
import com.pranavsailor.user_management.entities.User;
import com.pranavsailor.user_management.repositories.RoleRepository;
import com.pranavsailor.user_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;

    }

    public User createAdministrator(RegisterUserDto input){
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()){
            return null;
        }

        var user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }
}
