package com.cartera.model;

import com.cartera.model.pk.PersonaIdiomaId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona_idioma")
@IdClass(PersonaIdiomaId.class)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaIdioma {

    @Id
    @Column(name = "id_persona")
    private Long idPersona;

    @Id
    @Column(name = "id_idioma")
    private Integer idIdioma;

    @Column(name = "nivel", length = 50)
    private String nivel;
}
