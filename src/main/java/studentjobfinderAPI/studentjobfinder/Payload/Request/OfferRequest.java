package studentjobfinderAPI.studentjobfinder.Payload.Request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import studentjobfinderAPI.studentjobfinder.Model.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OfferRequest {
	@Id
	private Integer id;
	private String title;
	private OfferStatus status;
	private Integer nbrPlaces;
	private String context;
	private String mission;
	private String searchedProfile;
	private Address address;
	private Language language;
	private List<Student> candidates;
	private double salary;
	private contractType contract;
	private boolean remoteWork;
	private  String level;
	private Date startDate;
	private Date endDate;
	private String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	private Company company;
	private  String sector;
	
	public boolean isRemoteWork() {
		return remoteWork;
	}

	public void setRemoteWork(boolean remoteWork) {
		this.remoteWork = remoteWork;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	public Integer getNbrPlaces() {
		return nbrPlaces;
	}

	public void setNbrPlaces(Integer nbrPlaces) {
		this.nbrPlaces = nbrPlaces;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public String getSearchedProfile() {
		return searchedProfile;
	}

	public void setSearchedProfile(String searchedProfile) {
		this.searchedProfile = searchedProfile;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public List<Student> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Student> candidates) {
		this.candidates = candidates;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public contractType getContract() {
		return contract;
	}

	public void setContract(contractType contract) {
		this.contract = contract;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
	
}
