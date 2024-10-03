package com.example.janesdb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="janesDbInfo")
public class JanesDBEntity {

    @Id
    @GeneratedValue
    @Column(name="uuid")
    private UUID uuid;

    @Column(name="country")
    private String country;

    @Column(name = "shipLength",  precision = 6, scale = 2)
    private BigDecimal shipLength;

    @Column(name = "shipBeam",  precision = 6, scale = 2)
    private BigDecimal shipBeam;

    @Column(name="vesselClass")
    private String vesselClass;

}
