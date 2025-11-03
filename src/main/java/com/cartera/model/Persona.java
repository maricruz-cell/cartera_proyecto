package com.cartera.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sb_persona", schema = "public")
public class Persona {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "folio", length = 20, nullable = false)
    private String folio;

    @Column(name = "curp", length = 18, nullable = false)
    private String curp;

    @Column(name = "rfc", length = 13)
    private String rfc;

    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;

    @Column(name = "apellido_paterno", length = 60)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 60)
    private String apellidoMaterno;

    @Column(name = "correo", length = 100, nullable = false, unique = true)
    private String correo;

    /*@Column(name = "contrasena_base64", length = 255, nullable = false)
    private String contrasenaBase64;*/
    @Column(name = "contrasena_base64")
    private String contrasenaBase64;


    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "token_validacion", length = 255)
    private String tokenValidacion;

    // ======== CONSTRUCTORES ========

    public Persona() {
    }

    public Persona(String folio, String curp, String rfc, String nombre, String apellidoPaterno,
                   String apellidoMaterno, String correo, String contrasenaBase64,
                   Boolean activo, LocalDateTime fechaCreacion, String tokenValidacion) {
        this.folio = folio;
        this.curp = curp;
        this.rfc = rfc;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.contrasenaBase64 = contrasenaBase64;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.tokenValidacion = tokenValidacion;
    }

    // ======== GETTERS & SETTERS ========

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaBase64() {
        return contrasenaBase64;
    }

    public void setContrasenaBase64(String contrasenaBase64) {
        this.contrasenaBase64 = contrasenaBase64;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTokenValidacion() {
        return tokenValidacion;
    }

    public void setTokenValidacion(String tokenValidacion) {
        this.tokenValidacion = tokenValidacion;
    }

}
