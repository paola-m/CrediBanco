package com.crediBanco.crediBancoCard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Entity
@Table(name ="card", schema = "bankinc")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @Column(name = "card_id")
    private BigInteger cardId;

    @Column(name = "product_id")
    private Integer productId ;

    @Column(name = "holder_name")
    private String holderName ;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "is_active")
    private Integer isActive ;

    @Column(name = "is_blocked")
    private Integer isBlockend;

    @Column(name = "balance")
    private Integer balance;

}
