package com.fluxo.api_fluxo.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "product_info")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @Column(name = "product_SKU", nullable = false, unique = true)
    private String productSKU;

    @Column(name = "product_category", nullable = false)
    private String productCategory;

    @Column(name = "product_brand")
    private String productBrand;

    @Column(name = "product_model")
    private String productModel;
}
