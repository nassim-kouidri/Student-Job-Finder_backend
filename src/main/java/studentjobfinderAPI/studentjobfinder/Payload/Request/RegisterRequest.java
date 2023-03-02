package studentjobfinderAPI.studentjobfinder.Payload.Request;

import lombok.Getter;
import lombok.Setter;
import studentjobfinderAPI.studentjobfinder.Model.Address;
import studentjobfinderAPI.studentjobfinder.Model.Offer;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RegisterRequest {

	
	private String email;
	private String password;

	private  String name;
	private  String fname;
	private Address address;
	private Date birthDate;
	private String phoneNumber;
	private String description;
	private String creationDate;
	private String sector;
	private  String website;


   private List<Offer> offers;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getFname() {
		return fname;
	}

	public Address getAddress() {
		return address;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getDescription() {
		return description;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getSector() {
		return sector;
	}

	public String getWebsite() {
		return website;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
}
