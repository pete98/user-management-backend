package com.pranavsailor.user_management.services;

import com.pranavsailor.user_management.dtos.LoginUserDto;
import com.pranavsailor.user_management.dtos.RegisterUserDto;
import com.pranavsailor.user_management.entities.Role;
import com.pranavsailor.user_management.entities.RoleEnum;
import com.pranavsailor.user_management.entities.User;
import com.pranavsailor.user_management.repositories.RoleRepository;
import com.pranavsailor.user_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ){
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User signup(RegisterUserDto input){

       Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
       User user = new User();
       user.setFullName(input.getFullName());
       user.setEmail(input.getEmail());
       user.setPassword(passwordEncoder.encode(input.getPassword()));
       user.setRole(optionalRole.get());

       return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();

    }
}
