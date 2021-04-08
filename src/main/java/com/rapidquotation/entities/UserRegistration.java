package com.rapidquotation.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.rapidquotation.annotation.ContactNumberConstraint;

@Entity
@Table(name = "Registration_detail")
public class UserRegistration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotBlank(message = "*company name is mandetory")
	@Length(min = 5 , message = "*company name should be minimum 5 characters")
	private String companyName;
	
	@NotBlank(message = "*Email is mandetory")
	@Email(regexp = "^(.+)@(.+)$",message = "*please write valid email")
	private String email;
	
	@NotBlank(message = "*Password should not be blank")
	@Length(min=8 , message = "*password size should be minimum 8 characters" )
	//@Pattern(regexp = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})")
	private String password;
	
	@NotBlank(message = "*address is mandetory")
	private String address;
	
	@ContactNumberConstraint
	private String contactNumber;
	
	@Column(name = "Role")
	private String role;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public UserRegistration()
	{
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UserRegistration(int id, String companyName, String email, String password, String address,
			 String contactNumber) {
		this.id = id;
		this.companyName = companyName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.contactNumber = contactNumber;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserRegistration [id=" + id + ", companyName=" + companyName + ", email=" + email + ", password="
				+ password + ", address=" + address + ", contactNumber=" + contactNumber + ", role=" + role + "]";
	}
	
	
	
	

	

}
