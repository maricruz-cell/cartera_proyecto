package com.cartera.repository;

import com.cartera.model.CatSueldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatSueldoRepository extends JpaRepository<CatSueldo, Integer> {}
