package com.cartera.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cat_documento")
@Getter @Setter
public class CatDocumento {

    @Id
    @Column(name = "id_documento")
    private Integer idDocumento;

    @Column(name = "des_documento")
    private String descripcion;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "estatus")
    private Integer estatus;
}
