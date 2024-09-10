package com.kysp.banque.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "compte")
@Data
public class Compte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String email;
    private String accno;
    private String acType;
    private String username;
    private Long solde;
    private String password;
    private String role;
    
    
}
