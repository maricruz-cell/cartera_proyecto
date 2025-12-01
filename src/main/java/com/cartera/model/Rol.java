package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sb_rol", schema = "public")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol") // 👈 conecta con tu columna real en BD
    private Long idRol;

    @Column(name = "des_rol") // 👈 mantiene compatibilidad con la BD
    private String desRol;
}
