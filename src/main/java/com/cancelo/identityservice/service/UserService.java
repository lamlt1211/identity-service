package com.cancelo.identityservice.service;

import com.cancelo.identityservice.dto.request.ApiResponse;
import com.cancelo.identityservice.dto.request.UserCreationRequest;
import com.cancelo.identityservice.dto.request.UserUpdateRequest;
import com.cancelo.identityservice.dto.response.UserResponse;
import com.cancelo.identityservice.entity.User;
import com.cancelo.identityservice.enums.Role;
import com.cancelo.identityservice.exception.AppException;
import com.cancelo.identityservice.exception.ErrorCode;
import com.cancelo.identityservice.mapper.UserMapper;
import com.cancelo.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // cai nay thay cho @Autowired
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        // kiem tra user co ton tai hay khong
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request); // map mot lan, khong can map tay tung dong` mot
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found")));
    }
}
