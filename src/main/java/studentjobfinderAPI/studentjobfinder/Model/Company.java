package studentjobfinderAPI.studentjobfinder.Model;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Document("Company")

@Getter
@Setter
@JsonDeserialize

public class Company extends Account
{


	public Company(String email, String password, Role role, String name, String creationDate, String sector, String description, String website, Address address, String phoneNumber , List<Offer> offers,AccountStatus accountStatus) {
		super(email, password, role,accountStatus);
		this.name = name;
		this.creationDate = creationDate;
		this.sector = sector;
		this.description = description;
		this.address = address;
		this.website = website;
		this.phoneNumber = phoneNumber;
		this.offers = offers;

	}

	public Company (){
		
	}

	private String name;
	private String description;
	private Address address;
	@DBRef
	private Image logo;
	@DBRef
	private Image banner;
	private String creationDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

	private String sector;
	private String phoneNumber;
	private String presentation;
	@DBRef
	private List<Offer> offers;
	private  String linkedin;
	private  String website;
	private int numberOfEmployees;
	private int averageEmployeeAge;
	

	
	public int getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(int numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public int getAverageEmployeeAge() {
		return averageEmployeeAge;
	}

	public void setAverageEmployeeAge(int averageEmployeeAge) {
		this.averageEmployeeAge = averageEmployeeAge;
	}



	public String getPresentation() {
		return presentation;
	}



	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Address getAddress() {
		return address;
	}
	public Image getLogo() {
		return logo;
	}
	public Image getBanner() {
		return banner;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public String getWebsite() {
		return website;
	}

	public String getSector() {
		return sector;
	}
	public List<Offer> getOffers() {
		return offers;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public void setLogo(Image logo) {
		this.logo = logo;
	}
	public void setBanner(Image banner) {
		this.banner = banner;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
	public String getPhoneNumber() {
		// TODO Auto-generated method stub
		return phoneNumber;
	}






}