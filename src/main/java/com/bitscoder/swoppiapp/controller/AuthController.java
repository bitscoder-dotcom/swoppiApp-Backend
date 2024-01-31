package com.bitscoder.swoppiapp.controller;

import com.bitscoder.swoppiapp.dto.ApiResponse;
import com.bitscoder.swoppiapp.dto.SignInRequest;
import com.bitscoder.swoppiapp.dto.UserRegistrationRequest;
import com.bitscoder.swoppiapp.security.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/swoppiApp/v1/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegistrationRequest.Response>> register(@RequestBody UserRegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<SignInRequest.Response>> signIn(@RequestBody SignInRequest request) {
        return authService.signIn(request);
    }
}
