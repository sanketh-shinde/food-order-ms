package com.foodorder.user.dto.request;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RestaurantAdminRegistrationRequest extends UserRegistrationRequest {
    // No additional fields for now, can extend later
}
