package com.duberlyguarnizo.courierapp.employee;

import com.duberlyguarnizo.courierapp.enums.EmployeeRole;
import com.duberlyguarnizo.courierapp.enums.UserStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Optional<Employee> findByUsernameIgnoreCase(String username);

    Optional<Employee> findByIdCard(String idCard);

    Page<Employee> findAllByNamesContainingIgnoreCase(String names, Pageable pageable);

    Page<Employee> findAllByRole(EmployeeRole role, Pageable pageable);

    Page<Employee> findAllByStatus(UserStatus status, Pageable pageable);

    //JpaSpecification for employee filter
    static @NotNull Specification<Employee> filterByNames(@Nullable String names) {
        return (root, query, criteriaBuilder) -> {
            if (names == null || names.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("names"), "%" + names + "%");
        };
    }

    static @NotNull Specification<Employee> filterByStatus(@Nullable UserStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    static @NotNull Specification<Employee> filterByRole(@Nullable EmployeeRole role) {
        return (root, query, criteriaBuilder) -> {
            if (role == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("role"), role);
        };
    }

    static @NotNull Specification<Employee> filterByDate(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : LocalDate.now().minusDays(7).atStartOfDay();
        LocalDateTime end = endDate != null ? endDate.plusDays(1).atStartOfDay() : LocalDateTime.now();
        return (receiver, query, cb) -> cb.between(receiver.get("lastModifiedDate"), start, end);
    }

    default @NotNull Page<Employee> filter(String name, UserStatus status, EmployeeRole role, LocalDate startDate, LocalDate endDate, @NotNull Pageable pageable) {
        return findAll(filterByNames(name)
                .and(filterByStatus(status))
                .and(filterByRole(role))
                .and(filterByDate(startDate, endDate)), pageable);
    }


}
