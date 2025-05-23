package com.foodorder.user.controller;

import com.foodorder.user.dto.request.CustomerRegistrationRequest;
import com.foodorder.user.dto.request.DeliveryAgentRegistrationRequest;
import com.foodorder.user.dto.request.EmployeeRegistrationRequest;
import com.foodorder.user.dto.request.RestaurantAdminRegistrationRequest;
import com.foodorder.user.response.Response;
import com.foodorder.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "UserController")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register/customer")
    public ResponseEntity<Response> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest request) {
        log.info("Entering /register/customer api");
        Response response = userService.registerCustomer(request);
        log.info("Exiting /register/customer api");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/delivery-agent")
    public ResponseEntity<Response> registerDeliveryAgent(@Valid @RequestBody DeliveryAgentRegistrationRequest request) {
        log.info("Entering /register/delivery-agent api");
        Response response = userService.registerDeliveryAgent(request);
        log.info("Exiting /register/delivery-agent api");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/restaurant-admin")
    public ResponseEntity<Response> registerRestaurantAdmin(@Valid @RequestBody RestaurantAdminRegistrationRequest request) {
        log.info("Entering /register/restaurant-admin api");
        Response response = userService.registerRestaurantAdmin(request);
        log.info("Exiting /register/restaurant-admin api");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/employee")
    public ResponseEntity<Response> registerEmployee(@Valid @RequestBody EmployeeRegistrationRequest request) {
        log.info("Entering /register/employee api");
        Response response = userService.registerEmployee(request);
        log.info("Exiting /register/employee api");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String all() {
        return "all";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('DELIVERY_AGENT')")
    public int all(@PathVariable int id) {
        return id;
    }

}
