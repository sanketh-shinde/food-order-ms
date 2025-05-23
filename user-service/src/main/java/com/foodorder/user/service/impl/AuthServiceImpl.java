package com.foodorder.user.service.impl;

import com.foodorder.user.constants.Role;
import com.foodorder.user.entity.User;
import com.foodorder.user.jwt.JwtUtil;
import com.foodorder.user.mapper.UserMapper;
import com.foodorder.user.repository.*;
import com.foodorder.user.request.AuthRequest;
import com.foodorder.user.response.Response;
import com.foodorder.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.foodorder.user.constants.ConfigurationConstants.*;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryAgentRepository deliveryAgentRepository;
    private final EmployeeRepository employeeRepository;
    private final RestaurantAdminRepository restaurantAdminRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           DeliveryAgentRepository deliveryAgentRepository,
                           EmployeeRepository employeeRepository,
                           RestaurantAdminRepository restaurantAdminRepository,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.deliveryAgentRepository = deliveryAgentRepository;
        this.employeeRepository = employeeRepository;
        this.restaurantAdminRepository = restaurantAdminRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Response login(AuthRequest authRequest, Role role) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword()));
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        if (!user.getRoles().contains(role)) {
            throw new IllegalArgumentException("User does not have role: " + role.name());
        }
        Object response = getLoginResponseByRole(user, role);
        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRoles());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        Map<String, Object> result = new HashMap<>();
        result.put("user", response);
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        log.info("Exiting from login method");
        return Response.builder()
                .status(SUCCESS)
                .message(LOGIN_SUCCESS)
                .result(result)
                .build();
    }

    private Object getLoginResponseByRole(User user, Role role) {
        return switch (role) {
            case CUSTOMER -> customerRepository.findById(user.getId())
                    .map(UserMapper::mapCustomerToDto)
                    .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
            case DELIVERY_AGENT -> deliveryAgentRepository.findById(user.getId())
                    .map(UserMapper::mapDeliveryAgentToDto)
                    .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
            case RESTAURANT_ADMIN -> restaurantAdminRepository.findById(user.getId())
                    .map(UserMapper::mapRestaurantAdminToDto)
                    .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
            case EMPLOYEE, RESTAURANT_ONBOARDING, CUSTOMER_SUPPORT, EMPLOYEE_ADMIN ->
                    employeeRepository.findById(user.getId())
                            .map(UserMapper::mapEmployeeToDto)
                            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        };
    }

}
