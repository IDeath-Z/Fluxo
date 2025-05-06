package com.fluxo.api_fluxo.domain.product;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "technical_info")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private ProductInfo product;

    @Column(precision = 10, scale = 2)
    private BigDecimal productWeight;

    @Column(precision = 10, scale = 2)
    private BigDecimal productLength;

    @Column(precision = 10, scale = 2)
    private BigDecimal productWidth;

    @Column(precision = 10, scale = 2)
    private BigDecimal productHeight;
}
