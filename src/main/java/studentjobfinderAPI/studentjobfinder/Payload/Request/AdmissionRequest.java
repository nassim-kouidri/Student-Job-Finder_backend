package studentjobfinderAPI.studentjobfinder.Payload.Request;

import studentjobfinderAPI.studentjobfinder.Model.Admission;
import studentjobfinderAPI.studentjobfinder.Model.Candidacy;
import studentjobfinderAPI.studentjobfinder.Model.OfferCandidacy;

public class AdmissionRequest {
	
	Admission admission;
	Candidacy candidacy;
	OfferCandidacy offerCandidacy;
	
	public Admission getAdmission() {
		return admission;
	}
	public Candidacy getCandidacy() {
		return candidacy;
	}
	public OfferCandidacy getOfferCandidacy() {
		return offerCandidacy;
	}
	public void setAdmission(Admission admission) {
		this.admission = admission;
	}
	public void setCandidacy(Candidacy candidacy) {
		this.candidacy = candidacy;
	}
	public void setOfferCandidacy(OfferCandidacy offerCandidacy) {
		this.offerCandidacy = offerCandidacy;
	}
	

}
