package com.cartera.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona_especial")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaEspecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_persona")
    private Long idPersona;

    private String descripcion;

    private String nivel;
}
