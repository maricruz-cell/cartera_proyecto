package com.cartera.auth.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sb_rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "des_rol", nullable = false)
    private String desRol;

    // Getters y Setters
    public Long getIdRol() { return idRol; }
    public void setIdRol(Long idRol) { this.idRol = idRol; }

    public String getDesRol() { return desRol; }
    public void setDesRol(String desRol) { this.desRol = desRol; }
}
