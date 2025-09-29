package com.cartera.auth.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios") // solo por consistencia, pero no se usa BD aún
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String curp;  // será el "username"

    @Column(nullable = false, length = 100)
    private String password;

    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String email;
    private Integer cveUsergroup; // rol

    private boolean passwordCaducada = true;

    @Transient
    private String tempPassword;

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoP() { return apellidoP; }
    public void setApellidoP(String apellidoP) { this.apellidoP = apellidoP; }

    public String getApellidoM() { return apellidoM; }
    public void setApellidoM(String apellidoM) { this.apellidoM = apellidoM; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getCveUsergroup() { return cveUsergroup; }
    public void setCveUsergroup(Integer cveUsergroup) { this.cveUsergroup = cveUsergroup; }

    public boolean isPasswordCaducada() { return passwordCaducada; }
    public void setPasswordCaducada(boolean passwordCaducada) { this.passwordCaducada = passwordCaducada; }

    public String getTempPassword() { return tempPassword; }
    public void setTempPassword(String tempPassword) { this.tempPassword = tempPassword; }

    @Transient
    private Direccion direccion;

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }


}
