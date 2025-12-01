package com.cartera.repository;

import com.cartera.model.CatSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatSectorRepository extends JpaRepository<CatSector, Integer> {}
