package com.cartera.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_tipo_plaza")
public class CatTipoPlaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_plaza")
    private Integer idTipoPlaza;

    @Column(name = "desc_tipo_plaza")
    private String desTipoPlaza;


}
