package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_motivo_separacion")
public class CatMotivoSeparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motivo")
    private Integer idMotivo;

    @Column(name = "des_motivo")
    private String desMotivo;

    @Column(name = "estatus")
    private Short estatus;
}
