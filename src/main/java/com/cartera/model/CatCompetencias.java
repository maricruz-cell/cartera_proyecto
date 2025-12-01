package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_competencias")
public class CatCompetencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompetencia;

    @Column(name = "desc_competencia")
    private String descCompetencia;

    @Column(name = "tipo")
    private String tipo;  // <-- ESTE FALTABA
}
