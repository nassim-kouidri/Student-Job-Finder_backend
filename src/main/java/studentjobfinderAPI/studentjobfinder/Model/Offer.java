package studentjobfinderAPI.studentjobfinder.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("Offer")
public class Offer {
	
	@Id
	private String id;
	private String title;
	private OfferStatus status;
	private Integer nbrPlaces;
	private String context;
	private String mission;
	private String searchedProfile;
	private Address address;
	private Language language;
	private String companyId;
	private double salary;
	private contractType contract;
	private boolean remoteWork;
	private String level;
	private Date startDate;
	private Date endDate;
	private List<OfferCandidacy> offerCandidacies = Collections.emptyList();
	private String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	private List<String> questions;
	private  String  sector;
	private Reports reports = new Reports();
	
	public Reports getReports() {
		return reports;
	}
	public void setReport(Reports reports) {
		this.reports = reports;
	}
	public List<OfferCandidacy> getOfferCandidacies() {
		return offerCandidacies;
	}
	public void setOfferCandidacies(List<OfferCandidacy> offerCandidacies) {
		this.offerCandidacies = offerCandidacies;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public OfferStatus getStatus() {
		return status;
	}
	public Integer getNbrPlaces() {
		return nbrPlaces;
	}
	public String getContext() {
		return context;
	}
	public String getMission() {
		return mission;
	}
	public String getSearchedProfile() {
		return searchedProfile;
	}
	public Address getAddress() {
		return address;
	}
	public Language getLanguage() {
		return language;
	}

	public double getSalary() {
		return salary;
	}
	public contractType getContract() {
		return contract;
	}
	public boolean isRemoteWork() {
		return remoteWork;
	}
	public String getLevel() {
		return level;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public List<String> getQuestions() {
		return questions;
	}

	public String getSector() {
		return sector;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setStatus(OfferStatus status) {
		this.status = status;
	}
	public void setNbrPlaces(Integer nbrPlaces) {
		this.nbrPlaces = nbrPlaces;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	public void setSearchedProfile(String searchedProfile) {
		this.searchedProfile = searchedProfile;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	public void setContract(contractType contract) {
		this.contract = contract;
	}
	public void setRemoteWork(boolean remoteWork) {
		this.remoteWork = remoteWork;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
}
