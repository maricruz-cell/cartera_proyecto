package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sb_persona", schema = "public")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;

    private String folio;
    private String curp;
    private String rfc;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String correo;
    private String contrasena_base64;
    private Boolean activo;
    private LocalDateTime fecha_creacion;

    @Column(name = "token_validacion")
    private String tokenValidacion;

    @Column(name = "primer_login")
    private Boolean primerLogin;



}
