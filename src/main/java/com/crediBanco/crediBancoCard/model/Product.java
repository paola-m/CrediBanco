package com.crediBanco.crediBancoCard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="product", schema = "bankinc")
@Getter
@Setter
@Builder
public class Product {
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;
}
