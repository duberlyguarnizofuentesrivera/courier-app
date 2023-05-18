package com.duberlyguarnizo.courierapp.security;

import com.duberlyguarnizo.courierapp.employee.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeRepository repository;

    public CustomUserDetailsService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var employee = repository.findByUsernameIgnoreCase(username);
        return employee.orElseThrow(() -> new UsernameNotFoundException("CustomUserDetails: No user found with username: " + username));
    }
}
