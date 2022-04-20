package com.hackfse.eauction.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackfse.eauction.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
