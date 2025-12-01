package com.cartera.repository;

import com.cartera.model.PersonaEspecial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonaEspecialRepository extends JpaRepository<PersonaEspecial, Integer> {

    List<PersonaEspecial> findByIdPersona(Long idPersona);

    void deleteByIdPersona(Long idPersona);
}
