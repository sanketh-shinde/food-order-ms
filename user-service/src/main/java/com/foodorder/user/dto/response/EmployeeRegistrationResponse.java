package com.foodorder.user.dto.response;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegistrationResponse extends UserRegistrationResponse {

    private String department;
    private String employeeCode;
    private boolean onDuty;

}
