package studentjobfinderAPI.studentjobfinder.Payload.Request;

public class FilteredCompanyRequest {
	
	private String city;
	private String sector;
	private String keyword;
	
	
	public String getKeyword() {
		return keyword;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	

}
