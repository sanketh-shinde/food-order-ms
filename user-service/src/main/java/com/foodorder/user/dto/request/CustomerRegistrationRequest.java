package com.foodorder.user.dto.request;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CustomerRegistrationRequest extends UserRegistrationRequest {
    // No additional fields for now, can extend later
}
