package com.cartera.model.pk;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaInformaticaId implements Serializable {
    private Long idPersona;
    private Integer idInformatica;
}
