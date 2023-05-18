package com.duberlyguarnizo.courierapp.client;

import com.duberlyguarnizo.courierapp.address.Address;
import com.duberlyguarnizo.courierapp.enums.PersonType;
import com.duberlyguarnizo.courierapp.enums.UserStatus;
import com.duberlyguarnizo.courierapp.ticket.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    private String phone;
    @NotBlank
    private String idCard;

    @ToString.Exclude
    @ManyToOne
    private Address address;
    @ToString.Exclude
    @OneToMany
    private Set<Ticket> tickets;

    @Enumerated(EnumType.STRING)
    private PersonType clientType;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
