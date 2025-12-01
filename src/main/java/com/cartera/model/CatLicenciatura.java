package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cat_licenciaturas")
public class CatLicenciatura {

    @Id
    @Column(name = "id_licenciatura")
    private Integer idLicenciatura;

    @Column(name = "des_licenciatura")
    private String desLicenciatura;
}
