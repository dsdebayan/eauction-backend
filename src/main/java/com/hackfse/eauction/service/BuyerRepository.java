package com.hackfse.eauction.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackfse.eauction.entities.Buyer;
import com.hackfse.eauction.entities.Seller;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer>{
	
	Optional<Buyer> findByEmail(String email);

}
