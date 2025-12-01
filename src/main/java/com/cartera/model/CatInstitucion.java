package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_institucion")
public class CatInstitucion {

    @Id
    @Column(name = "id_institucion")
    private Integer idInstitucion;

    @Column(name = "des_institucion")
    private String desInstitucion;
}
