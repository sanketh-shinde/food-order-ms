package com.foodorder.user.mapper;

import com.foodorder.user.dto.response.*;
import com.foodorder.user.entity.*;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static UserRegistrationResponse map(User user) {
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAddress(user.getAddress());
        response.setRoles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));
        return response;
    }

    public static <T extends UserRegistrationResponse> T mapUserToDto(User user, T targetDto) {
        if (user == null) return null;
        targetDto.setId(user.getId());
        targetDto.setUsername(user.getUsername());
        targetDto.setEmail(user.getEmail());
        targetDto.setPhoneNumber(user.getPhoneNumber());
        targetDto.setAddress(user.getAddress());
        targetDto.setCreatedAt(user.getCreatedAt());
        targetDto.setUpdatedAt(user.getUpdatedAt());
        targetDto.setActive(user.isActive());
        if (user.getRoles() != null) {
            Set<String> roles = user.getRoles()
                    .stream()
                    .map(Enum::name)
                    .collect(Collectors.toSet());
            targetDto.setRoles(roles);
        }
        return targetDto;
    }

    public static CustomerRegistrationResponse mapCustomerToDto(Customer customer) {
        // map field if available
        return mapUserToDto(customer.getUser(), new CustomerRegistrationResponse());
    }

    public static DeliveryAgentRegistrationResponse mapDeliveryAgentToDto(DeliveryAgent deliveryAgent) {
        DeliveryAgentRegistrationResponse response = mapUserToDto(deliveryAgent.getUser(),
                new DeliveryAgentRegistrationResponse());
        if (response != null) {
            response.setLicenseNumber(deliveryAgent.getLicenseNumber());
            response.setVehicleNumber(deliveryAgent.getVehicleNumber());
            response.setAvailable(deliveryAgent.isAvailable());
        }
        return response;
    }

    public static EmployeeRegistrationResponse mapEmployeeToDto(Employee employee) {
        EmployeeRegistrationResponse response = mapUserToDto(employee.getUser(),
                new EmployeeRegistrationResponse());
        if (response != null) {
            response.setDepartment(employee.getDepartment());
            response.setEmployeeCode(employee.getEmployeeCode());
            response.setOnDuty(employee.isOnDuty());
        }
        return response;
    }

    public static RestaurantAdminRegistrationResponse mapRestaurantAdminToDto(RestaurantAdmin admin) {
        // map field if available
        return UserMapper.mapUserToDto(admin.getUser(), new RestaurantAdminRegistrationResponse());
    }

}
