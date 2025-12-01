package com.cartera.repository;

import com.cartera.model.CatGrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CatGradoRepository extends JpaRepository<CatGrado, Integer> {
}
