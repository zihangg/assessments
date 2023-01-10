package edag.assessment.repository;

import org.springframework.stereotype.Repository;

import edag.assessment.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
    List<Product> findByCode(String code);
}
