package com.crediBanco.crediBancoCard.repository;

import com.crediBanco.crediBancoCard.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;


@Repository
public interface CardRepository extends JpaRepository<Card, Serializable> {

}
