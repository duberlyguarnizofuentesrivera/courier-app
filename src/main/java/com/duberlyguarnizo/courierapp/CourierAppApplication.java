package com.duberlyguarnizo.courierapp;

import com.duberlyguarnizo.courierapp.employee.EmployeeService;
import com.duberlyguarnizo.courierapp.employee.dto.CreateEmployeeDto;
import com.duberlyguarnizo.courierapp.employee.dto.DetailEmployeeDto;
import com.duberlyguarnizo.courierapp.enums.EmployeeRole;
import com.duberlyguarnizo.courierapp.enums.UserStatus;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableWebSecurity
@Slf4j
public class CourierAppApplication implements CommandLineRunner {
    @Resource
    EmployeeService employeeService;

    public static void main(String[] args) {
        SpringApplication.run(CourierAppApplication.class, args);
    }

    @Override
    public void run(String... args) {
        SimpleCommandLinePropertySource propertySource = new SimpleCommandLinePropertySource(args);

        if (propertySource.containsProperty("create-admin") &&
                propertySource.containsProperty("admin-password")) {
            String newAdminUsername = propertySource.getProperty("create-admin");
            String newAdminPassword = propertySource.getProperty("admin-password");

            Optional<DetailEmployeeDto> firstUser = employeeService.getEmployeeByUsername(newAdminUsername);
            if (firstUser.isEmpty()) {
                if (newAdminPassword == null || newAdminPassword.isEmpty()) {
                    log.warn("There was an attempt to create a first admin user, but the password was null or invalid!");
                } else {
                    log.warn("Creating new admin user!");
                    CreateEmployeeDto newUser = CreateEmployeeDto.builder()
                            .names("Duberly Guarnizo")
                            .idCard("922618630")
                            .email("duberlygfr@gmail.com")
                            .phoneNumber("922618630")
                            .role(EmployeeRole.ADMIN)
                            .status(UserStatus.ACTIVE)
                            .username(newAdminUsername)
                            .password(newAdminPassword)
                            .build();
                    employeeService.createEmployee(newUser);
                    log.warn("Admin user created successfully");
                }
            }
        } else {
            log.warn("There was an unsuccessful attempt to create an existing first admin user!");
        }
    }
}
