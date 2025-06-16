package com.foodorder.user.controller;

import com.foodorder.user.config.RabbitMQConfig;
import com.foodorder.user.dto.request.CustomerRegistrationRequest;
import com.foodorder.user.dto.request.DeliveryAgentRegistrationRequest;
import com.foodorder.user.dto.request.EmployeeRegistrationRequest;
import com.foodorder.user.dto.request.RestaurantAdminRegistrationRequest;
import com.foodorder.user.dto.response.Response;
import com.foodorder.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users/register")
@Tag(name = "User Registration", description = "Endpoints for user role-based registration")
public class UserController {

    private final UserService userService;
    private final RabbitTemplate template;

    public UserController(UserService userService, RabbitTemplate template) {
        this.userService = userService;
        this.template = template;
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable int id) {
        template.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, id);
    }

    @PostMapping("/customer")
    @Operation(summary = "Register a Customer", description = "Creates a new customer account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response> registerCustomer(
            @Parameter(description = "Customer registration request", required = true)
            @Valid @RequestBody CustomerRegistrationRequest request) {
        log.info("Entering /register/customer api");
        Response response = userService.registerCustomer(request);
        log.info("Exiting /register/customer api");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delivery-agent")
    @Operation(summary = "Register a Delivery Agent", description = "Creates a new delivery agent account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery agent registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response> registerDeliveryAgent(
            @Parameter(description = "Delivery agent registration request", required = true)
            @Valid @RequestBody DeliveryAgentRegistrationRequest request) {
        log.info("Entering /register/delivery-agent api");
        Response response = userService.registerDeliveryAgent(request);
        log.info("Exiting /register/delivery-agent api");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/restaurant-admin")
    @Operation(summary = "Register a Restaurant Admin", description = "Creates a new restaurant admin account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant admin registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response> registerRestaurantAdmin(
            @Parameter(description = "Restaurant admin registration request", required = true)
            @Valid @RequestBody RestaurantAdminRegistrationRequest request) {
        log.info("Entering /register/restaurant-admin api");
        Response response = userService.registerRestaurantAdmin(request);
        log.info("Exiting /register/restaurant-admin api");
        return ResponseEntity.ok(response);
    }

    @PostMapping(name = "/employee", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register an Employee", description = "Creates a new employee account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Response> registerEmployee(
            @Parameter(description = "Employee registration request", required = true)
            @Valid @RequestBody EmployeeRegistrationRequest request) {
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
