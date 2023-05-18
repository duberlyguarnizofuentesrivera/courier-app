package com.duberlyguarnizo.courierapp.employee;

import com.duberlyguarnizo.courierapp.employee.dto.BasicEmployeeDto;
import com.duberlyguarnizo.courierapp.employee.dto.CreateEmployeeDto;
import com.duberlyguarnizo.courierapp.employee.dto.DetailEmployeeDto;
import com.duberlyguarnizo.courierapp.employee.mappers.EmployeeMapper;
import com.duberlyguarnizo.courierapp.enums.EmployeeRole;
import com.duberlyguarnizo.courierapp.enums.UserStatus;
import com.duberlyguarnizo.courierapp.util.exception.ExistingEntityException;
import com.duberlyguarnizo.courierapp.util.exception.InvalidFieldValueException;
import com.duberlyguarnizo.courierapp.util.exception.NonExistingEntityException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final @NotNull EmployeeRepository employeeRepository;
    private final @NotNull EmployeeMapper employeeMapper;
    private final @NotNull PasswordEncoder  passwordEncoder;


    public Long createEmployee(@NotNull CreateEmployeeDto dto){
        //check that a user does not exist with the same username or idCard
        var empSameUsername = employeeRepository.findByUsernameIgnoreCase(dto.username());
        if (empSameUsername.isPresent()) {
            throw new ExistingEntityException("El nombre de usuario ya existe", empSameUsername.get().getUsername());
        } else {
            var empSameIdCard = employeeRepository.findByIdCard(dto.idCard());
            if (empSameIdCard.isPresent()) {
                throw new ExistingEntityException("El DNI/RUC ya existe", empSameIdCard.get().getIdCard());
            } else {
                Employee employee = employeeMapper.toEmployee(dto);
                employee.setPassword(passwordEncoder.encode(dto.password()));
                try {
                    Employee savedEmployee = employeeRepository.save(employee);
                    return savedEmployee.getId();
                } catch (IllegalArgumentException e) {
                    throw new InvalidFieldValueException("No se pudo crear al empleado: faltan datos", "Todos los campos");
                }
            }
        }
    }

    public @NotNull Optional<DetailEmployeeDto> getEmployee(@NotNull Long id) {
        var employee = employeeRepository.findById(id);
        return employee.map(employeeMapper::toDetailEmployeeDto);
    }

    public @NotNull Page<BasicEmployeeDto> filter(String name,
                                                  @NotNull String status,
                                                  @NotNull String role,
                                                  LocalDate startDate,
                                                  LocalDate endDate,
                                                  @Nullable Pageable pageable) throws InvalidFieldValueException {
        UserStatus userStatus = null;
        EmployeeRole employeeRole = null;
        if (!status.equals("all")) {
            try {
                userStatus = UserStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                throw new InvalidFieldValueException("El filtro de estado de usuario no es válido", "Estado de empleado");
            }
        }
        if (!role.equals("all")) {
            try {
                employeeRole = EmployeeRole.valueOf(role);
            } catch (IllegalArgumentException e) {
                throw new InvalidFieldValueException("El filtro de estado de usuario no es válido", "Rol de empleado");
            }
        }
        if (pageable == null) {
            pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "names"));
        }
        var employeeResult = employeeRepository.filter(name, userStatus, employeeRole, startDate, endDate, pageable);
        return employeeResult.map(employeeMapper::toBasicEmployeeDto);
    }

    public void deleteEmployee(@NotNull Long id) {
        var employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new NonExistingEntityException("No existe un empleado con el id indicado: " + id, "Id de empleado");
        }
        if (!employee.get().getStatus().equals(UserStatus.INACTIVE)) {
            throw new InvalidFieldValueException("El empleado no está inactivo. Debes desactivar a un usuario antes de eliminarlo.", "Estado de empleado");
        }
        try {
            employeeRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new NonExistingEntityException("El empleado no pudo ser eliminado. El Id indicado es nulo.", "Empleado");
        }
    }

    public @NotNull Optional<DetailEmployeeDto> getEmployeeByUsername(String employeeUsername) {
        var employee = employeeRepository.findByUsernameIgnoreCase(employeeUsername);
        return employee.map(employeeMapper::toDetailEmployeeDto);
    }
}
