package com.duberlyguarnizo.courierapp.employee;


import com.duberlyguarnizo.courierapp.employee.dto.CreateEmployeeDto;
import com.duberlyguarnizo.courierapp.employee.dto.DetailEmployeeDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
public class EmployeeAPIController {

    private final EmployeeService employeeService;

    public EmployeeAPIController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public @NotNull ResponseEntity<DetailEmployeeDto> getEmployee(@PathVariable @NotNull Long id) {
        log.warn("EmployeeAPIController.getEmployee");
        return ResponseEntity.of(employeeService.getEmployee(id));
    }

    @PostMapping("/")
    public @NotNull ResponseEntity<Long> createEmployee(@Valid @RequestBody @NotNull CreateEmployeeDto employee) {
        log.warn("Attempting to create employee");
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

//    @PutMapping("/{id}")
//    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
//        return employeeService.updateEmployee(id, employee);
//    }

    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Boolean> deleteEmployee(@PathVariable @NotNull Long id) {
        log.warn("Attempting to delete employee!");
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}

