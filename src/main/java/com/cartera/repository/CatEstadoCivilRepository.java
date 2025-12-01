package com.cartera.repository;
import com.cartera.model.CatEstadoCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatEstadoCivilRepository extends JpaRepository<CatEstadoCivil, Integer> {

}
