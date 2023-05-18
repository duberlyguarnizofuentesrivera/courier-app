package com.duberlyguarnizo.courierapp.employee;

import com.duberlyguarnizo.courierapp.address.Address;
import com.duberlyguarnizo.courierapp.auditing.AuditableEntity;
import com.duberlyguarnizo.courierapp.enums.EmployeeRole;
import com.duberlyguarnizo.courierapp.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee extends AuditableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String names;
    @NotBlank
    @Column(unique = true)
    private String idCard;
    private String phoneNumber;
    @Email
    private String email;

    @ManyToOne
    private Address address;

    @NotBlank
    private String password;
    @Column(unique = true)
    @Length(min = 5)
    @NotBlank
    private String username;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

    @Override
    public @NotNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
}


