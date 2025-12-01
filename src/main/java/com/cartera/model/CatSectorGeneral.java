package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_sector_general")
public class CatSectorGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sector_general")
    private Integer idSectorGeneral;

    @Column(name = "des_sector_general")
    private String desSectorGeneral;

    @Column(name = "subsector")
    private String subsector;

    @Column(name = "estatus")
    private Short estatus;
}
