package com.duberlyguarnizo.courierapp.employee.dto;

import com.duberlyguarnizo.courierapp.enums.EmployeeRole;
import com.duberlyguarnizo.courierapp.enums.UserStatus;

public record BasicEmployeeDto(
        Long id,
        String names,
        String username,
        EmployeeRole role,
        UserStatus status

) {
}
