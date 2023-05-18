package com.duberlyguarnizo.courierapp.employee.dto;

import com.duberlyguarnizo.courierapp.address.Address;
import com.duberlyguarnizo.courierapp.enums.EmployeeRole;
import com.duberlyguarnizo.courierapp.enums.UserStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DetailEmployeeDto(
        Address address,
        String email,
        Long id,
        String idCard,
        String names,
        String password,
        String phoneNumber,
        EmployeeRole role,
        UserStatus status,
        String username,
        Long createdBy,
        LocalDateTime createdDate,
        Long lastModifiedBy,
        LocalDateTime lastModifiedDate,
        String notes
) {

}
