package com.cartera.model;

import com.cartera.model.pk.PersonaInformaticaId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona_informatica")
@IdClass(PersonaInformaticaId.class)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaInformatica {

    @Id
    @Column(name = "id_persona")
    private Long idPersona;

    @Id
    @Column(name = "id_informatica")
    private Integer idInformatica;

    @Column(name = "nivel", length = 50)
    private String nivel;
}
