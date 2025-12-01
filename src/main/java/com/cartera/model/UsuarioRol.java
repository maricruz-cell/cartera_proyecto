package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sb_usuario_rol", schema = "public")
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario_rol;

    @Column(name = "id_persona")
    private Long id_persona;

    @Column(name = "id_rol")
    private Long id_rol;
}
