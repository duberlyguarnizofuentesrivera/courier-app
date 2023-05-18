package com.duberlyguarnizo.courierapp.receiver;

import com.duberlyguarnizo.courierapp.enums.PersonType;
import com.duberlyguarnizo.courierapp.shipment.Shipment;
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
public class Receiver {
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
    @OneToMany
    private Set<Shipment> shipments;

    @Enumerated(EnumType.STRING)
    private PersonType receiverType;
}
