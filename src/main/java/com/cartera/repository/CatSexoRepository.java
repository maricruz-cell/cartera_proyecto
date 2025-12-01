package com.cartera.repository;
import com.cartera.model.CatSexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CatSexoRepository extends JpaRepository<CatSexo, String> {

}
