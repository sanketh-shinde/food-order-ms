package com.foodorder.user.dto.request;

import com.foodorder.user.constants.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s ]+$", message = "Full name must contain only letters and spaces")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(regexp = "^[a-zA-Z0-9.]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$",
            message = "Email must be a valid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*?&)")
    private String password;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,11}$", message = "Phone number must be a valid format")
    @Size(max = 13, message = "Phone number must not exceed 13 characters")
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @NotNull(message = "Role is required")
    private Set<Role> roles;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    private boolean active = true;

}
