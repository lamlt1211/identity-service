package com.cancelo.identityservice.service;

import com.cancelo.identityservice.dto.request.UserCreationRequest;
import com.cancelo.identityservice.dto.request.UserUpdateRequest;
import com.cancelo.identityservice.dto.response.UserResponse;
import com.cancelo.identityservice.entity.User;
import com.cancelo.identityservice.exception.AppException;
import com.cancelo.identityservice.exception.ErrorCode;
import com.cancelo.identityservice.mapper.UserMapper;
import com.cancelo.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // cai nay thay cho @Autowired
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    public User createUser(UserCreationRequest request) {
        // kiem tra user co ton tai hay khong
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request); // map mot lan, khong can map tay tung dong` mot
        return userRepository.save(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        userMapper.updateUser(user, request);

//        user.setPassword(request.getPassword());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());

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
