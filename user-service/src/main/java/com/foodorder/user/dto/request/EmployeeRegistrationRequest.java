package com.foodorder.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegistrationRequest extends UserRegistrationRequest {

    @NotBlank(message = "department is required")
    private String department;

    private boolean onDuty;

}