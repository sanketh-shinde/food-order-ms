package com.foodorder.user.controller;

import com.foodorder.user.constants.Role;
import com.foodorder.user.request.AuthRequest;
import com.foodorder.user.response.Response;
import com.foodorder.user.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody AuthRequest authRequest, @RequestParam("role") Role role) {
        log.info("Entering login api");
        Response response = authService.login(authRequest, role);
        log.info("Exiting login api");
        return ResponseEntity.ok(response);
    }

}
