package studentjobfinderAPI.studentjobfinder.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studentjobfinderAPI.studentjobfinder.Model.*;
import studentjobfinderAPI.studentjobfinder.Repository.AdmissionRepository;
import studentjobfinderAPI.studentjobfinder.Repository.CompanyRepository;
import studentjobfinderAPI.studentjobfinder.Repository.OfferRepository;
import studentjobfinderAPI.studentjobfinder.Repository.StudentRepository;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AdmissionService {
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	AdmissionRepository admissionRepository;


	public Student getStudentFromToken(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		int endEmail = payload.indexOf(",");
		String studentEmail = payload.substring(8, endEmail - 1);
		return studentRepository.findStudentByEmail(studentEmail);
	}


	public List<Admission> admissionList(String token) {
		Student student1 = getStudentFromToken(token);
		return admissionRepository.findAdmissionByStudentId(student1.getId());
	}

	public void addAdmissionStudent(Student student, Candidacy candidacy) {
		student.getCandidacies().add(candidacy);
		studentRepository.save(student);

	}
	public List<Admission> admissionListComp(String token , Offer offer) {
		Company company = getCompanyFromToken(token);
		List<Admission> admissions = null;
		for (int i = 0; i < company.getOffers().size(); i++) {
			if (company.getOffers().get(i).getId().equals(offer.getId())) ;

			admissions = admissionRepository.findAdmissionByOfferId(offer.getId());

		}
		return admissions;
	}


	public void addAdmissionOffer(Offer offer, OfferCandidacy offerCandidacy) {
		offer.getOfferCandidacies().add(offerCandidacy);
		offerRepository.save(offer);
	}

	public String candidate(String token, Admission admission, String offerId, Candidacy candidacy, OfferCandidacy offerCandidacy) {
		// Check if the offer exists
		Optional<Offer> e = offerRepository.findById(offerId);
		if (!e.isPresent()) {
			return "Incorrect offerId";
		}
		// Get the student from the token
		Student student = getStudentFromToken(token);
		admission.setStudent(student);
		Offer existingOffer = e.get();
		// Check if the student is already a candidate for this offer
		if (admissionRepository.findByOfferIdAndStudentId(offerId, student.getId()).isPresent()) {
			return "You have already submitted your application to this offer";
		}
		admission.setOffer(existingOffer);
		admission.setAdmissionStatus(AdmissionStatus.SENT);
		candidacy.setAnswers(admission.getAnswers());
		candidacy.setOfferId(admission.getOffer().getId());
		offerCandidacy.setAnswers(admission.getAnswers());
		offerCandidacy.setStudent(admission.getStudent());
		addAdmissionStudent(student, candidacy);
		addAdmissionOffer(existingOffer, offerCandidacy);
		admissionRepository.save(admission);
		return "You have successfully submitted your application to this offer";
	}


	public Company getCompanyFromToken(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		int endEmail = payload.indexOf(",");
		String companyEmail = payload.substring(8, endEmail - 1);
		return companyRepository.findCompanyByEmail(companyEmail);
	}

	public String admissionProgress(String token , Admission admission) {
		Company company = getCompanyFromToken(token);

		for(int i=0 ; i< company.getOffers().size(); i++) {
			if(admission.getOffer().getId().equals(company.getOffers().get(i).getId())) {
				if(admission.getAdmissionStatus().equals(AdmissionStatus.SENT) ) {
					admission.setAdmissionStatus(AdmissionStatus.PROGRESS);

					admissionRepository.save(admission);
					return "Admission is In Progress";
				}
			}
		}
		return  "Admission is  " + admission.getAdmissionStatus();
	}

	public String admissionAccepted(String token , Admission admission) {
		Company company = getCompanyFromToken(token);

		for(int i=0 ; i< company.getOffers().size(); i++) {
			if(admission.getOffer().getId().equals(company.getOffers().get(i).getId())) {

					admission.setAdmissionStatus(AdmissionStatus.ACCEPTED);

					admissionRepository.save(admission);
					return "Admission is Accepted";

			}
		}
		return  "Admission is  " + admission.getAdmissionStatus();
	}


	public String admissionRejected(String token , Admission admission) {
		Company company = getCompanyFromToken(token);

		for(int i=0 ; i< company.getOffers().size(); i++) {
			if(admission.getOffer().getId().equals(company.getOffers().get(i).getId())) {

				admission.setAdmissionStatus(AdmissionStatus.REJECTED);

				admissionRepository.save(admission);
				return "Admission is Rejected";

			}
		}
		return  "Admission is  " + admission.getAdmissionStatus();
	}

}
