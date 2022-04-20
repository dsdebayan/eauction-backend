package com.hackfse.eauction.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//@ApiModel(description="All details about the product.")
@Entity
public class Product {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	
	private String shortDesc;
	
	private String detailedDesc;
	
	private String category;
	
	@PositiveOrZero
	//@ApiModelProperty(notes="startPrice should be a valid numeric")
	private Double startPrice;
	
	@Future
	//@ApiModelProperty(notes="bidEndDate should be a future date")
	private Date bidEndDate;
	
	@JsonProperty("seller")
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Seller seller;

	public Product(Integer id, String name, String shortDesc, String detailedDesc, String category,
			@PositiveOrZero Double startPrice, @Future Date bidEndDate) {
		super();
		this.id = id;
		this.name = name;
		this.shortDesc = shortDesc;
		this.detailedDesc = detailedDesc;
		this.category = category;
		this.startPrice = startPrice;
		this.bidEndDate = bidEndDate;
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", shortDesc=" + shortDesc + ", detailedDesc=" + detailedDesc
				+ ", category=" + category + ", startPrice=" + startPrice + ", bidEndDate=" + bidEndDate + "]";
	}

		


}
