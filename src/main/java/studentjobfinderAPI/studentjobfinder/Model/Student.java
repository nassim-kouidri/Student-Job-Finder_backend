package studentjobfinderAPI.studentjobfinder.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Document("Student")
@Getter
@Setter

public class
Student extends Account {


	public List<Candidacy> getCandidacies() {
		return candidacies;
	}

	public void setCandidacies(List<Candidacy> candidacies) {
		this.candidacies = candidacies;
	}

	public contractType getContratType() {
		return contractType;
	}

	public void setContratType(contractType contratType) {
		this.contractType = contratType;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	@JsonIgnore
	public List <String> getFavOffersId() {
		return favOffersId;
	}

	public void setFavOffersId(List <String> favOffersId) {
		this.favOffersId = favOffersId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public ArrayList<Long> getNote() {
		return note;
	}

	public void setNote(ArrayList<Long> note) {
		this.note = note;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
	public Student(String email, String password, Role role, String name, String fname, Date birthDate, String phoneNumber, AccountStatus accountStatus) {
		super(email, password,role,accountStatus);
		this.name = name;
		this.fname = fname;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.candidacies= Collections.emptyList();
		this.favOffersId= Collections.emptyList();
	}

	public Student (){

	}



	private String name;
	private String fname;
	private Date birthDate;
	private String biography;
	private String phoneNumber;
	private String area;
	private String level;
	private  ArrayList<Long> note;

	private contractType contractType;
	@DBRef
	private  Image image;
	private String sector;
	private List<Candidacy> candidacies;


	private  List <String> favOffersId;
	private  String linkedin;
	private  String github;

	private String website;
	private String diplomaSector;

	
	public contractType getContractType() {
		return contractType;
	}

	public void setContractType(contractType contractType) {
		this.contractType = contractType;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDiplomaSector() {
		return diplomaSector;
	}

	public void setDiplomaSector(String diplomaSector) {
		this.diplomaSector = diplomaSector;
	}
}
