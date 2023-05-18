package com.duberlyguarnizo.courierapp.ticket;

import com.duberlyguarnizo.courierapp.enums.TicketPaymentStatus;
import com.duberlyguarnizo.courierapp.shipment.Shipment;
import com.duberlyguarnizo.courierapp.tracking.TrackingCode;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    @ToString.Exclude
    private @NotNull Set<Shipment> shipments = new HashSet<>();
    private TicketPaymentStatus paymentStatus;
    @OneToOne
    private TrackingCode trackingCode;
}
