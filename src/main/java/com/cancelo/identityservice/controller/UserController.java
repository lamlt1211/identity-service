package com.cancelo.identityservice.controller;

import com.cancelo.identityservice.dto.request.ApiResponse;
import com.cancelo.identityservice.dto.request.UserCreationRequest;
import com.cancelo.identityservice.dto.request.UserUpdateRequest;
import com.cancelo.identityservice.dto.response.UserResponse;
import com.cancelo.identityservice.entity.User;
import com.cancelo.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    List<User> getAllUsers() {
        var authencatication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authencatication.getName());
        authencatication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));


        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User deleted";
    }
}
