package com.foodorder.user.controller;

import com.foodorder.user.constants.Role;
import com.foodorder.user.dto.request.AuthRequest;
import com.foodorder.user.dto.response.Response;
import com.foodorder.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "APIs for user authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "User Login",
            description = "Authenticate user based on credentials and role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<Response> login(
            @Parameter(description = "Authentication request payload", required = true)
            @Valid @RequestBody AuthRequest authRequest,
            @Parameter(description = "User role for login", required = true)
            @RequestParam("role") Role role
    ) {
        log.info("Entering login api");
        Response response = authService.login(authRequest, role);
        log.info("Exiting login api");
        return ResponseEntity.ok(response);
    }

}
