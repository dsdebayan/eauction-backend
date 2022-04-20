package com.hackfse.eauction.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackfse.eauction.entities.Bid;
import com.hackfse.eauction.entities.Buyer;
import com.hackfse.eauction.entities.Product;
import com.hackfse.eauction.service.BidRepository;
import com.hackfse.eauction.service.BuyerRepository;
import com.hackfse.eauction.service.ProductRepository;

@RestController
@RequestMapping("/e-auction/api/v1/buyer")
public class BuyerController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BuyerRepository buyerRepository;

	@Autowired
	private BidRepository bidRepository;

	@PostMapping("/place-bid")
	public ResponseEntity<Void> placeBid(@Valid @RequestBody Bid bid) {

		Buyer buyer;
		Optional<Buyer> buyerOptional = buyerRepository.findByEmail(
				bid.getBuyer().getEmail());

		if(!buyerOptional.isPresent()) {
			buyer = buyerRepository.save(bid.getBuyer());
		} else {
			buyer = buyerOptional.get();
		}
		Optional<Product> productOptional = productRepository.findById(
				bid.getProduct().getId());
		if(!productOptional.isPresent()) {
			throw new RuntimeException("Product Id : "
					+ bid.getProduct().getId() 
					+ " doesn't exist");
		}

		Product product = productOptional.get();

		LocalDate date = LocalDate.now();
		LocalDate bidEndDate = product.getBidEndDate().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		if (date.isAfter(bidEndDate)) {
			throw new RuntimeException(" Bid is not allowed pass bidEndDate : " + bidEndDate);
		}

		Optional<Bid> bidOptional = bidRepository
				.findByProductIdAndBuyerId(product.getId(), buyer.getId());
		if(bidOptional.isPresent()) {
			throw new RuntimeException("Duplicate Bid not allowed on product id : " + product.getId()
			+ " and buyer email : " + buyer.getEmail());

		}

		bidRepository.save(bid);

		return ResponseEntity.created(null).build();

	}

	@PutMapping("/updatebid/{productId}/{buyerEmailld}/{newBidAmount}")
	public ResponseEntity<Object> updateBid(@PathVariable int productId, @PathVariable String buyerEmailld, @PathVariable Double newBidAmount ) {

		Optional<Buyer> buyerOptional = buyerRepository
				.findByEmail(buyerEmailld);

		if (!buyerOptional.isPresent()) {
			throw new RuntimeException("Buyer : " + buyerEmailld + "doesn't exist");
		}

		Buyer buyer = buyerOptional.get();

		Optional<Bid> bidOptional = bidRepository
				.findByProductIdAndBuyerId(productId, buyer.getId());

		if (!bidOptional.isPresent()) {
			throw new RuntimeException("Bid doesn't exist. Buyer Id : " + buyer.getId()
			+ "product Id : " + productId);
		}

		Bid bid = bidOptional.get();
		bid.setBidAmount(newBidAmount);

		Product product = bid.getProduct();

		LocalDate date = LocalDate.now();
		LocalDate bidEndDate = product.getBidEndDate().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		if (date.isAfter(bidEndDate)) {
			throw new RuntimeException("Bid update not allowed past bidEndDate : " + bidEndDate);
		}

		bidRepository.save(bid);

		return ResponseEntity.noContent().build();
	}

}