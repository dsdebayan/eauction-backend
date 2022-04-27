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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackfse.eauction.beans.BidInfo;
import com.hackfse.eauction.beans.ProductInfo;
import com.hackfse.eauction.entities.Bid;
import com.hackfse.eauction.entities.Product;
import com.hackfse.eauction.service.BidRepository;
import com.hackfse.eauction.service.ProductRepository;
import com.hackfse.eauction.service.SellerRepository;

@RestController
@RequestMapping("/e-auction/api/v1/seller")
public class SellerController {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BidRepository bidRepository;

	@PostMapping("/add-product")
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

	@DeleteMapping("/delete/{productId}")
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

	@GetMapping("/show-bids/{productId}")
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
				bidInfo = new BidInfo(bid.getId(), bid.getBuyer().getFirstName(), bid.getBuyer().getEmail(), 
						bid.getBuyer().getPhone(), bid.getBidAmount());
				bidInfos.add(bidInfo);
			}
		}
		bidProductInfo.setBids(bidInfos);

		return bidProductInfo;
	}

}