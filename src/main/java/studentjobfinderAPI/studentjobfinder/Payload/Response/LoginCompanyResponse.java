package studentjobfinderAPI.studentjobfinder.Payload.Response;

import java.util.List;

import studentjobfinderAPI.studentjobfinder.Model.Company;
import studentjobfinderAPI.studentjobfinder.Model.Offer;

public class LoginCompanyResponse {
	
	private Company company;
	private List<Offer> offers;
	
	public Company getCompany() {
		return company;
	}
	public List<Offer> getOffers() {
		return offers;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public void setOffers(List<Offer> list) {
		this.offers = list;
	}
	
	

}
