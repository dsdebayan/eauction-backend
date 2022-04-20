package com.hackfse.eauction.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackfse.eauction.beans.BidInfo;
import com.hackfse.eauction.beans.ProductInfo;
import com.hackfse.eauction.entities.Buyer;
import com.hackfse.eauction.entities.Bid;
import com.hackfse.eauction.entities.Product;
import com.hackfse.eauction.service.BidRepository;
import com.hackfse.eauction.service.BuyerRepository;
import com.hackfse.eauction.service.ProductRepository;
import com.hackfse.eauction.service.SellerRepository;

@RestController
public class AuctionController {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BuyerRepository buyerRepository;

	@Autowired
	private BidRepository bidRepository;

	@PostMapping("/e-auction/api/v1/seller/add-product")
	public ResponseEntity<Void> addProduct(@Valid @RequestBody Product product) {

		List<String> categories = Arrays.asList("Painting", "Sculptor", "Ornaments");
		if (!categories.contains(product.getCategory())){
			throw new RuntimeException(product.getCategory() 
					+ " should be within " + categories);
		}
		sellerRepository.save(product.getSeller());
		productRepository.save(product);

		return ResponseEntity.created(null).build();
	}

	@DeleteMapping("/e-auction/api/v1/seller/delete/{productId}")
	public void deleteProduct(@PathVariable int productId) {
		Optional<Product> productOptional = productRepository.findById(productId);
		if(!productOptional.isPresent()) {
			throw new RuntimeException("Product Id : " + productId + " doesn't exist");
		}
		Product product = productOptional.get();

		LocalDate date = LocalDate.now();
		LocalDate bidEndDate = product.getBidEndDate().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		if (date.isAfter(bidEndDate)) {
			throw new RuntimeException(" Product delete is not allowed pass bidEndDate : " + bidEndDate);
		}

		Optional<List<Bid>> bidOptional = bidRepository.findByProductId(productId);
		if(bidOptional.isPresent() && bidOptional.get().size() > 0) {
			throw new RuntimeException(" Product delete is not allowed as " 
					+ bidOptional.get().size() + " bids being present");
		}
		productRepository.deleteById(productId);

	}

	@PostMapping("/e-auction/api/v1/buyer/place-bid")
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

	@GetMapping("/e-auction/api/v1/seller/show-bids/{productId}")
	public ProductInfo showBids(@PathVariable int productId) {
		Optional<Product> productOptional = productRepository.findById(productId);

		if(!productOptional.isPresent()) {
			throw new RuntimeException("Product Id : " + productId + "doesn't exist");
		}

		Product product = productOptional.get();

		ProductInfo bidProductInfo = new ProductInfo();
		bidProductInfo.setProductName(product.getName());
		bidProductInfo.setShortDesc(product.getShortDesc());
		bidProductInfo.setDetailedDesc(product.getDetailedDesc());
		bidProductInfo.setCategory(product.getCategory());
		bidProductInfo.setStartPrice(product.getStartPrice());
		bidProductInfo.setBidEndDate(product.getBidEndDate());

		Optional<List<Bid>> bidOptional = bidRepository.findByProductId(productId, 
				Sort.by(Sort.Direction.DESC, "bidAmount"));
		List<BidInfo> bidInfos = new ArrayList<>();
		if(bidOptional.isPresent()) {
			List<Bid> bids = bidOptional.get();

			BidInfo bidInfo;
			for (Bid bid : bids) {
				bidInfo = new BidInfo(bid.getBuyer().getFirstName(), bid.getBuyer().getEmail(), 
						bid.getBuyer().getPhone(), bid.getBidAmount());
				bidInfos.add(bidInfo);
			}
		}
		bidProductInfo.setBids(bidInfos);

		return bidProductInfo;
	}

	@PutMapping("/e-auction/api/v1/buyer/updatebid/{productId}/{buyerEmailld}/{newBidAmount}")
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