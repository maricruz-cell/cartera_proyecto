package com.cartera.repository;
import com.cartera.model.CatNacionalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatNacionalidadRepository extends JpaRepository<CatNacionalidad, Integer> {

}
