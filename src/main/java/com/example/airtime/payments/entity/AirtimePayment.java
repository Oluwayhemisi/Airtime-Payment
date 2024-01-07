package com.example.airtime.payments.entity;

import com.example.airtime.payments.enums.Status;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class AirtimePayment {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uniqueCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "airtime_id", nullable = false)
    private AirtimeDetails airtimeDetails;

    private String referenceId;
    private String requestId;


    @Enumerated(EnumType.STRING)
    private Status status;


}
