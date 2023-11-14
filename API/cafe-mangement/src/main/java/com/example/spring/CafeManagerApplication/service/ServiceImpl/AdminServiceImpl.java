package com.example.spring.CafeManagerApplication.service.ServiceImpl;

import com.example.spring.CafeManagerApplication.entity.UserEntity;
import com.example.spring.CafeManagerApplication.exception.UserNotFound;
import com.example.spring.CafeManagerApplication.repository.UserRepository;
import com.example.spring.CafeManagerApplication.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<?> enableUser(Long userId) {
        Optional<UserEntity> optional = userRepository.findById(userId);

        if(optional.isEmpty()) throw new UserNotFound("User not found");

        UserEntity user = optional.get();
        user.setActive(true);

        return new ResponseEntity<>("Active account successfully", HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> getUser() {
        List<UserEntity> users = userRepository.findAll();

        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
