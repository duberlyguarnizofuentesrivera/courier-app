package com.duberlyguarnizo.courierapp.security;

import com.duberlyguarnizo.courierapp.employee.Employee;
import com.duberlyguarnizo.courierapp.employee.EmployeeService;
import com.duberlyguarnizo.courierapp.employee.dto.BasicEmployeeDto;
import com.duberlyguarnizo.courierapp.payload.request.LoginRequest;
import com.duberlyguarnizo.courierapp.util.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final @NotNull AuthenticationManager authenticationManager;
    private final @NotNull EmployeeService employeeService;
    private final @NotNull PasswordEncoder encoder;
    private final @NotNull JwtUtils jwtUtils;


    @PostMapping("/signin")
    public @NotNull ResponseEntity<?> authenticateUser(@Valid @RequestBody @NotNull LoginRequest loginRequest) {
        log.warn("entering authenticateUser");
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Employee employee = (Employee) authentication.getPrincipal();
        log.warn("Authentication Employee (Principal): {}", employee);
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(employee);
        log.warn("Cookie: {}", jwtCookie.toString());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new BasicEmployeeDto(employee.getId(),
                        employee.getNames(),
                        employee.getUsername(),
                        employee.getRole(),
                        employee.getStatus()));
    }

    @PostMapping("/signout")
    public @NotNull ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }
}
