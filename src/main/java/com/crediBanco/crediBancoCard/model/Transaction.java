package com.crediBanco.crediBancoCard.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name ="transaction_", schema = "banKinc")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "transaction_type")
    private String transactionType ;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "status")
    private String status ;

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id")
    private Card cardId ;
}
