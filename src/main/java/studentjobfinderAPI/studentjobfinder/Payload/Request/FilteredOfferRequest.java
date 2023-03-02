package studentjobfinderAPI.studentjobfinder.Payload.Request;

import studentjobfinderAPI.studentjobfinder.Model.contractType;

public class FilteredOfferRequest {
	
	private contractType contractType;
	private String level;
	private String keyword;
	private String city;
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public contractType getContractType() {
		return contractType;
	}
	public void setContractType(contractType contractType) {
		this.contractType = contractType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
