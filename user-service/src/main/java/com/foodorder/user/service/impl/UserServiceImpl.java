package com.foodorder.user.service.impl;

import com.foodorder.user.constants.Role;
import com.foodorder.user.dto.request.*;
import com.foodorder.user.entity.*;
import com.foodorder.user.mapper.UserMapper;
import com.foodorder.user.repository.*;
import com.foodorder.user.response.Response;
import com.foodorder.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.foodorder.user.constants.ConfigurationConstants.SUCCESS;
import static com.foodorder.user.constants.ConfigurationConstants.USER_SAVED;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryAgentRepository deliveryAgentRepository;
    private final EmployeeRepository employeeRepository;
    private final RestaurantAdminRepository restaurantAdminRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, CustomerRepository customerRepository, DeliveryAgentRepository deliveryAgentRepository, EmployeeRepository employeeRepository, RestaurantAdminRepository restaurantAdminRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.deliveryAgentRepository = deliveryAgentRepository;
        this.employeeRepository = employeeRepository;
        this.restaurantAdminRepository = restaurantAdminRepository;
        this.encoder = encoder;
    }

    @Override
    public Response registerCustomer(CustomerRegistrationRequest request) {
        log.info("Entering in customer registration service");
        Customer customer = new Customer();
        User user = createOrUpdateUser(request);
        customer.setUser(user);
        Customer savedCustomer = customerRepository.save(customer);
        Map<String, Object> result = new HashMap<>();
        result.put("user", UserMapper.mapCustomerToDto(savedCustomer));
        log.info("customer details saved successfully");
        log.info("Exiting from customer registration service");
        return Response.builder()
                .status(SUCCESS)
                .message(USER_SAVED)
                .result(result)
                .build();
    }

    @Override
    public Response registerDeliveryAgent(DeliveryAgentRegistrationRequest request) {
        log.info("Entering in delivery agent registration service");
        DeliveryAgent delivery = new DeliveryAgent();
        User user = createOrUpdateUser(request);
        delivery.setUser(user);
        delivery.setVehicleNumber(request.getVehicleNumber());
        delivery.setLicenseNumber(request.getLicenseNumber());
        delivery.setAvailable(request.isAvailable());
        DeliveryAgent deliveryAgent = deliveryAgentRepository.save(delivery);
        Map<String, Object> result = new HashMap<>();
        result.put("user", UserMapper.mapDeliveryAgentToDto(deliveryAgent));
        log.info("delivery agent details saved successfully");
        log.info("Exiting from delivery agent registration service");
        return Response.builder()
                .status(SUCCESS)
                .message(USER_SAVED)
                .result(result)
                .build();
    }

    @Override
    public Response registerRestaurantAdmin(RestaurantAdminRegistrationRequest request) {
        log.info("Entering in restaurant admin registration service");
        RestaurantAdmin admin = new RestaurantAdmin();
        User user = createOrUpdateUser(request);
        admin.setUser(user);
        RestaurantAdmin savedRestaurantAdmin = restaurantAdminRepository.save(admin);
        Map<String, Object> result = new HashMap<>();
        result.put("user", UserMapper.mapRestaurantAdminToDto(savedRestaurantAdmin));
        log.info("restaurant admin details saved successfully");
        log.info("Exiting from restaurant admin registration service");
        return Response.builder()
                .status(SUCCESS)
                .message(USER_SAVED)
                .result(result)
                .build();
    }

    @Override
    public Response registerEmployee(EmployeeRegistrationRequest request) {
        log.info("Entering in employee registration service");
        Employee employee = new Employee();
        User user = createOrUpdateUser(request);
        employee.setUser(user);
        employee.setDepartment(request.getDepartment());
        employee.setEmployeeCode(generateEmployeeCode(user.getId()));
        employee.setOnDuty(request.isOnDuty());
        Employee savedEmployee = employeeRepository.save(employee);
        Map<String, Object> result = new HashMap<>();
        result.put("user", UserMapper.mapEmployeeToDto(savedEmployee));
        log.info("employee details saved successfully");
        log.info("Exiting from employee registration service");
        return Response.builder()
                .status(SUCCESS)
                .message(USER_SAVED)
                .result(result)
                .build();
    }

    private User mapUser(UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setRoles(request.getRoles());
        user.setActive(request.isActive());
        return userRepository.save(user);
    }

    private User createOrUpdateUser(UserRegistrationRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(existingUser -> {
                    Set<Role> updatedRoles = new HashSet<>(existingUser.getRoles());
                    updatedRoles.addAll(request.getRoles());
                    existingUser.setRoles(updatedRoles);
                    return existingUser;
                })
                .orElseGet(() -> mapUser(request));
    }

    private String generateEmployeeCode(Long staffId) {
        return String.format("EMP%04d", staffId);
    }

}
