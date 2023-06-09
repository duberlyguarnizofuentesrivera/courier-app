package com.duberlyguarnizo.courierapp.auditing;

import com.duberlyguarnizo.courierapp.employee.Employee;
import com.duberlyguarnizo.courierapp.employee.EmployeeRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomAuditorAware implements AuditorAware<Long> {
    private final EmployeeRepository employeeRepository;

    public CustomAuditorAware(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public @NotNull Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        } else {
            Employee principal =
                    (Employee) authentication
                            .getPrincipal();
            String username = principal.getUsername();
            Optional<Employee> currentUser = employeeRepository.findByUsernameIgnoreCase(username);
            return currentUser.map(Employee::getId);
        }
    }
}
