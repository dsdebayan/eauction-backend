package com.hackfse.eauction.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackfse.eauction.entities.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer>{

}
