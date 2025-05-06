package com.fluxo.api_fluxo.domain.lot;

import java.time.LocalDate;

import com.fluxo.api_fluxo.domain.product.ProductInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Table(name = "lot_info")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LotInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductInfo product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierInfo supplier;

    @Column(nullable = false)
    private String lotCode;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private Integer remainingQuantity;

    @Column
    private String lotLocation;
}