package com.duberlyguarnizo.courierapp.tracking;

import com.duberlyguarnizo.courierapp.ticket.Ticket;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackingCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    @OneToOne
    private Ticket ticket;
}
