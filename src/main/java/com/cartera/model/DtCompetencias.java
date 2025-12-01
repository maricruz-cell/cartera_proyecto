package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "dt_competencias")
@Data
public class DtCompetencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_competencia_persona;

    private Long idPersona;

    private Long idCompetencia;

    private Boolean valor;
}
