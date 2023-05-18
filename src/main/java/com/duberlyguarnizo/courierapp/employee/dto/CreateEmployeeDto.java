package com.duberlyguarnizo.courierapp.employee.dto;

import com.duberlyguarnizo.courierapp.address.Address;
import com.duberlyguarnizo.courierapp.enums.EmployeeRole;
import com.duberlyguarnizo.courierapp.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateEmployeeDto(
        @NotBlank
        String names,
        Address address,
        @Email
        String email,
        @NotBlank
        String idCard,
        @NotBlank
        String username,
        @NotBlank
        String password,
        String phoneNumber,
        EmployeeRole role,
        UserStatus status
) {

}
