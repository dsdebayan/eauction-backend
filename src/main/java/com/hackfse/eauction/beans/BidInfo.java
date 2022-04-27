package com.hackfse.eauction.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class BidInfo {

	private int id;
	private String buyerName;
	
	private String email;
	
	private Integer phone;
	
	private Double bidAmount;

	public BidInfo(int id, String buyerName, String email, Integer phone, Double bidAmount) {
		super();
		this.id=id;
		this.buyerName = buyerName;
		this.email = email;
		this.phone = phone;
		this.bidAmount = bidAmount;
	}
	
	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Double getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(Double bidAmount) {
		this.bidAmount = bidAmount;
	}

	@Override
	public String toString() {
		return "BidInfo [buyerName=" + buyerName + ", email=" + email + ", phone=" + phone + ", bidAmount=" + bidAmount
				+ "]";
	}
	
	
	
}
