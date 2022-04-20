package com.hackfse.eauction.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

//@ApiModel(description="All details about the seller.")
@Entity
public class Seller {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(min=5, max=30, message="firstName should have min 5 and max 30 characters")
	//@ApiModelProperty(notes="firstName should have min 5 and max 30 characters")
	private String firstName;
	
	@Size(min=3, max=25, message="lastName should have min 3 and max 25 characters")
	//@ApiModelProperty(notes="lastName should have min 3 and max 25 characters")
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private int pin;
	
	@Digits(integer=10, fraction = 0, message="phone should be of 10 digits")
	//@ApiModelProperty(notes="phone should be of 10 digits")
	private int phone;
	
	@Email(message="should be a valid email")
	//@ApiModelProperty(notes="should be a valid email")
	private String email;
	
	@OneToMany(mappedBy="seller")
	private List<Product> products;

	public Seller(Integer id,
			@Size(min = 5, max = 30, message = "firstName should have min 5 and max 30 characters") String firstName,
			@Size(min = 3, max = 25, message = "lastName should have min 3 and max 25 characters") String lastName,
			String address, String city, String state, int pin,
			@Digits(integer = 10, fraction = 0, message = "phone should be of 10 digits") int phone,
			@Email(message = "should be a valid email") String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.pin = pin;
		this.phone = phone;
		this.email = email;
	}

	public Seller() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", city=" + city + ", state=" + state + ", pin=" + pin + ", phone=" + phone + ", email=" + email
				+ ", products=" + products + "]";
	}

		
}
