package com.example.tp_18.entities;

import com.example.tp_18.entities.TypeCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {

    @Id
    private String id;

    private float solde;

    private String dateCreation;

    @Enumerated(EnumType.STRING)
    private TypeCompte type;
}

