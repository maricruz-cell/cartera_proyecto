package com.cartera.model.pk;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaIdiomaId implements Serializable {
    private Long idPersona;
    private Integer idIdioma;
}
