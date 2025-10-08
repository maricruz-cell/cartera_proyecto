package com.cartera.auth.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sb_usuario_rol")
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_userol")
    private Long idUserol;

    @ManyToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    // Getters y Setters
    public Long getIdUserol() {
        return idUserol;
    }

    public void setIdUserol(Long idUserol) {
        this.idUserol = idUserol;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
