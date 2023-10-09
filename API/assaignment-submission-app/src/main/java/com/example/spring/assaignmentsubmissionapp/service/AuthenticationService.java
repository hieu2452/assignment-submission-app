package com.example.spring.assaignmentsubmissionapp.service;

import com.example.spring.assaignmentsubmissionapp.dto.AuthResponseDTO;
import com.example.spring.assaignmentsubmissionapp.dto.LoginDto;
import com.example.spring.assaignmentsubmissionapp.dto.RegisterDto;
import com.example.spring.assaignmentsubmissionapp.entity.Role;
import com.example.spring.assaignmentsubmissionapp.entity.UserEntity;
import com.example.spring.assaignmentsubmissionapp.repository.RoleRepository;
import com.example.spring.assaignmentsubmissionapp.repository.UserRepository;
import com.example.spring.assaignmentsubmissionapp.security.JwtGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtGenerator jwtGenerator;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, JwtGenerator jwtGenerator, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO register(RegisterDto registerDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName("STUDENT").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);
        var token = jwtGenerator.generateToken(user);
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginDto loginDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        var user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        var token = jwtGenerator.generateToken(user);
        return new AuthResponseDTO(token);
    }
}
