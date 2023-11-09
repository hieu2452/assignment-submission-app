package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.dto.AuthResponseDTO;
import com.example.spring.CafeManagerApplication.dto.LoginDto;
import com.example.spring.CafeManagerApplication.dto.RegisterDto;
import com.example.spring.CafeManagerApplication.entity.Role;
import com.example.spring.CafeManagerApplication.entity.UserEntity;
import com.example.spring.CafeManagerApplication.exception.UserNotAllow;
import com.example.spring.CafeManagerApplication.exception.UserNotFound;
import com.example.spring.CafeManagerApplication.repository.RoleRepository;
import com.example.spring.CafeManagerApplication.repository.UserRepository;
import com.example.spring.CafeManagerApplication.security.JwtGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class  AuthenticationService implements com.example.spring.CafeManagerApplication.service.AuthenticationService {
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

    @Transactional
    public ResponseEntity<?> register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("username already exist", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();

        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        Role role = roleRepository.findByName("employee").orElseThrow();
        user.setRoles(Collections.singletonList(role));

        UserEntity savedUser = userRepository.save(user);

        return new ResponseEntity<>( "Register successfully",HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> login(LoginDto loginDto) {

        if(!userRepository.existsByUsername(loginDto.getUsername())){
            return new ResponseEntity<>("Bad credential", HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        UserEntity user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow();

        if(!user.getActive()) {
            throw new UserNotAllow("Please wait for admin to grant you access to this application");
        }
        Map<String,List<String>> roles = mapRole(user.getRoles());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = jwtGenerator.generateToken(roles,user);
        return new ResponseEntity<>(new AuthResponseDTO(token, user.getUsername()),HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> enableUser(Long userId) {
        Optional<UserEntity> optional = userRepository.findById(userId);

        if(optional.isEmpty()) throw new UserNotFound("User not found");

        UserEntity user = optional.get();

        return new ResponseEntity<>(user,HttpStatus.NO_CONTENT);
    }

    private Map<String, List<String>> mapRole(List<Role> roles){
        List<String> roleName = roles.stream().map(Role::getName).toList();
        Map<String,List<String>> authorities = new HashMap<>();
        authorities.put("role",roleName);
        return authorities;
    }
}
