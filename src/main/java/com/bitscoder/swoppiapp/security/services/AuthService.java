package com.bitscoder.swoppiapp.security.services;

import com.bitscoder.swoppiapp.dto.ApiResponse;
import com.bitscoder.swoppiapp.dto.EmailDetails;
import com.bitscoder.swoppiapp.dto.SignInRequest;
import com.bitscoder.swoppiapp.dto.UserRegistrationRequest;
import com.bitscoder.swoppiapp.entities.BaseUser;
import com.bitscoder.swoppiapp.entities.Customer;
import com.bitscoder.swoppiapp.entities.Vendor;
import com.bitscoder.swoppiapp.enums.UserTypes;
import com.bitscoder.swoppiapp.repository.CustomerRepository;
import com.bitscoder.swoppiapp.repository.UserRepository;
import com.bitscoder.swoppiapp.repository.UsertypeRepository;
import com.bitscoder.swoppiapp.repository.VendorRepository;
import com.bitscoder.swoppiapp.security.jwt.JwtUtils;
import com.bitscoder.swoppiapp.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private AuthenticationManager authenticationManager;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;
    private UserRepository userRepository;
    private UsertypeRepository usertypeRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private UserDetailsServiceImpl userDetailsService;
    private EmailService emailService;


    @Transactional
    public ResponseEntity<ApiResponse<UserRegistrationRequest.Response>> register(UserRegistrationRequest request) {

        log.info("Register method called with request: {}", request);

        String username = request.getName();
        String email = request.getEmail();

        boolean usernameExists = userRepository.existsByName(username);
        boolean emailExists = userRepository.existsByEmail(email);

        if (usernameExists) {
            log.info("Username already taken: {}", username);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username is already taken!"
            );
        }

        if (emailExists) {
            log.info("Email already in use: {}", email);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email Address already in use!"
            );
        }

        BaseUser user;
        if (request.getUserType() == UserTypes.CUSTOMER) {
            user = new Customer();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setUserType(request.getUserType());
            customerRepository.save((Customer) user);
        } else if (request.getUserType() == UserTypes.VENDOR) {
            user = new Vendor();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setUserType(request.getUserType());
            vendorRepository.save((Vendor) user);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }

        log.info("User registered successfully with username: {}", username);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(user.getEmail());
        emailDetails.setSubject("Account Registration Confirmation");
        emailDetails.setMessageBody("Your account has been registered on SwoppiApp");
        emailService.sendEmail(emailDetails);

        UserRegistrationRequest.Response response = new UserRegistrationRequest.Response(
                LocalDateTime.now(),
                UUID.randomUUID().toString(),
                true,
                "User registered successfully",
                request.getName()
        );

        return createSuccessResponse("User registered successfully", response);
    }

    public ResponseEntity<ApiResponse<SignInRequest.Response>> signIn(SignInRequest request) {
        log.info("SignIn method called with email: {}", request.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            log.info("User signed in successfully with email: {}", request.getEmail());

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            Set<UserTypes> userTypes = userDetails.getAuthorities().stream()
                    .map(grantedAuthority -> UserTypes.valueOf(grantedAuthority.getAuthority()))
                    .collect(Collectors.toSet());

            SignInRequest.Response response = new SignInRequest.Response(
                    userDetails.getUserId(),
                    jwt,
                    "Bearer",
                    userDetails.getUsername(),
                    userTypes,
                    jwtUtils.getJwtExpirationDate()
            );

            return createSuccessResponse("User signed in successfully", response);
        } catch (BadCredentialsException e) {
            log.info("Invalid email or password for email: {}", request.getEmail());
            return createBadRequestResponse("Invalid email or password", null);
        }
    }

    public <T> ResponseEntity<ApiResponse<T>> createSuccessResponse(String message, T data) {
        return ResponseEntity.ok(new ApiResponse<>(
                LocalDateTime.now(),
                UUID.randomUUID().toString(),
                true,
                message,
                data
        ));
    }

    public <T> ResponseEntity<ApiResponse<T>> createBadRequestResponse(String message, T data) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        UUID.randomUUID().toString(),
                        false,
                        message,
                        data
                )
        );
    }
}
