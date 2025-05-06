package com.fluxo.api_fluxo.domain.lot;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "lot_operation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LotOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lot_id", nullable = false)
    private LotInfo lot;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime movementDate;

    @Column(nullable = false)
    private Integer movementAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column
    private String notes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Operations movementType;
}
