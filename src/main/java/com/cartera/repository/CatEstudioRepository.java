package com.cartera.repository;

import com.cartera.model.CatEstudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CatEstudioRepository extends JpaRepository<CatEstudio, Integer> {
}
