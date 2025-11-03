package com.cartera.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sb_usuario_rol", schema = "public")
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_userol")
    private Long idUserol;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    // Getters y setters
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
