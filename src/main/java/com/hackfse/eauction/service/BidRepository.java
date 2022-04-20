package com.hackfse.eauction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackfse.eauction.entities.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer>{
	Optional<List<Bid>> findByProductId(Integer id);
	Optional<Bid> findByProductIdAndBuyerId(Integer productId, Integer buyerId);
	Optional<List<Bid>> findByProductId(int productId, Sort by);

}
