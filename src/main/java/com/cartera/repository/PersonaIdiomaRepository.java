package com.cartera.repository;

import com.cartera.model.PersonaIdioma;
import com.cartera.model.pk.PersonaIdiomaId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonaIdiomaRepository extends JpaRepository<PersonaIdioma, PersonaIdiomaId> {

    List<PersonaIdioma> findByIdPersona(Long idPersona);

    void deleteByIdPersona(Long idPersona);
}
