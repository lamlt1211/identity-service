package com.cancelo.identityservice.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.cancelo.identityservice.validator.DobConstraint;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;
}
