package com.duberlyguarnizo.courierapp.employee.mappers;

import com.duberlyguarnizo.courierapp.employee.Employee;
import com.duberlyguarnizo.courierapp.employee.dto.BasicEmployeeDto;
import com.duberlyguarnizo.courierapp.employee.dto.CreateEmployeeDto;
import com.duberlyguarnizo.courierapp.employee.dto.DetailEmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    Employee toEmployee(CreateEmployeeDto employeeDto);

    BasicEmployeeDto toBasicEmployeeDto(Employee employee);

    DetailEmployeeDto toDetailEmployeeDto(Employee employee);
}
