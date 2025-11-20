package com.cartera.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sb_foto_usuario")
public class FotoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Long idFoto;

    @Column(name = "id_persona")
    private Long idPersona;   // ← CORREGIDO 🔥🔥🔥🔥🔥

    @Column(name = "ruta_foto")
    private String rutaFoto;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}
