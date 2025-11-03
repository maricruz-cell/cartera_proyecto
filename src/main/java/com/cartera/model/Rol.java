package com.cartera.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sb_rol", schema = "public")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "des_rol", length = 100, nullable = false)
    private String desRol;

    @Column(name = "nivel_acceso")
    private Short nivelAcceso;

    // Getters y setters
    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getDesRol() {
        return desRol;
    }

    public void setDesRol(String desRol) {
        this.desRol = desRol;
    }

    public Short getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(Short nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }
}
