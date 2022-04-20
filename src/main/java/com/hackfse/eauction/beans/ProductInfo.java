package com.hackfse.eauction.beans;

import java.util.Date;
import java.util.List;

public class ProductInfo {
	
	private String productName;
	private String shortDesc;

	private String detailedDesc;

	private String category;

	private Double startPrice;

	private Date bidEndDate;
	
	private List<BidInfo> bids;

	public ProductInfo(String productName, String shortDesc, String detailedDesc, String category, Double startPrice,
			Date bidEndDate, List<BidInfo> bids) {
		super();
		this.productName = productName;
		this.shortDesc = shortDesc;
		this.detailedDesc = detailedDesc;
		this.category = category;
		this.startPrice = startPrice;
		this.bidEndDate = bidEndDate;
		this.bids = bids;
	}

	public ProductInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getDetailedDesc() {
		return detailedDesc;
	}

	public void setDetailedDesc(String detailedDesc) {
		this.detailedDesc = detailedDesc;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public Date getBidEndDate() {
		return bidEndDate;
	}

	public void setBidEndDate(Date bidEndDate) {
		this.bidEndDate = bidEndDate;
	}

	public List<BidInfo> getBids() {
		return bids;
	}

	public void setBids(List<BidInfo> bids) {
		this.bids = bids;
	}

	@Override
	public String toString() {
		return "ProductInfo [productName=" + productName + ", shortDesc=" + shortDesc + ", detailedDesc=" + detailedDesc
				+ ", category=" + category + ", startPrice=" + startPrice + ", bidEndDate=" + bidEndDate + ", bids="
				+ bids + "]";
	}
	
	
}
