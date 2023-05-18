package com.duberlyguarnizo.courierapp.shipment;

import com.duberlyguarnizo.courierapp.address.Address;
import com.duberlyguarnizo.courierapp.client.Client;
import com.duberlyguarnizo.courierapp.employee.Employee;
import com.duberlyguarnizo.courierapp.enums.ShipmentProblem;
import com.duberlyguarnizo.courierapp.enums.ShipmentStatus;
import com.duberlyguarnizo.courierapp.receiver.Receiver;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Receiver receiver;
    @ManyToOne
    private Employee transporter;
    @ManyToOne
    private Employee courier;
    @ManyToOne
    private Employee dispatcher;
    @ManyToOne
    private Address address;

    private String contactName;
    private String contactPhone;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
    @Enumerated(EnumType.STRING)
    private ShipmentProblem problem;
    @Positive
    private Double cost;
    @Min(1)
    private int numberOfPackages;
    @ElementCollection
    @ToString.Exclude
    private @NotNull Set<String> photoUrls=new HashSet<>();
    private String deliveryNotes;
    @PastOrPresent
    private LocalDateTime receptionDate;
    @PastOrPresent
    private LocalDateTime onRouteDate;
    @PastOrPresent
    private LocalDateTime onReturnDate;
    @PastOrPresent
    private LocalDateTime returnDate;
    @PastOrPresent
    private LocalDateTime deliveryDate;
}
