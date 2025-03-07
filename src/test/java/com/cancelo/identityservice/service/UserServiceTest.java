package com.cancelo.identityservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.cancelo.identityservice.dto.request.UserCreationRequest;
import com.cancelo.identityservice.dto.response.UserResponse;
import com.cancelo.identityservice.entity.User;
import com.cancelo.identityservice.exception.AppException;
import com.cancelo.identityservice.repository.UserRepository;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest userCreationRequest;

    private UserResponse userResponse;

    private User user;

    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1999, 12, 11);
        userCreationRequest = UserCreationRequest.builder()
                .username("lamletung")
                .firstName("le")
                .lastName("lam")
                .password("12345678")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("1")
                .username("lamletung")
                .firstName("le")
                .lastName("lam")
                .dob(dob)
                .build();

        user = User.builder()
                .id("1a2b3c4d")
                .username("lamletung")
                .firstName("le")
                .lastName("lam")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        // when
        var response = userService.createUser(userCreationRequest);
        // then
        Assertions.assertThat(response.getId()).isEqualTo("1a2b3c4d");
        Assertions.assertThat(response.getUsername()).isEqualTo("le");
    }

    @Test
    void createUser_userExisted_fail() {
        // given
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        // when
        var exception = assertThrows(AppException.class, () -> userService.createUser(userCreationRequest));
        // then
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "lamlt")
    void getMyInfo_valid_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        var response = userService.getMyInfo();
        Assertions.assertThat(response.getUsername()).isEqualTo("1a2b3c4d");
        Assertions.assertThat(response.getId()).isEqualTo("1a2b3c4d");
    }

    @Test
    @WithMockUser(username = "lamlt")
    void getMyInfo_userNotFound_error() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));
        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }
}
