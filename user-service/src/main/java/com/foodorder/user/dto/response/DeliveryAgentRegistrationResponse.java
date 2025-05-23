package com.foodorder.user.dto.response;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAgentRegistrationResponse extends UserRegistrationResponse {

    private String vehicleNumber;
    private String licenseNumber;
    private boolean available;

}
