package studentjobfinderAPI.studentjobfinder.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import studentjobfinderAPI.studentjobfinder.Model.*;
import studentjobfinderAPI.studentjobfinder.Payload.Request.FilteredCompanyRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.FilteredOfferRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.PasswordChangeRequest;
import studentjobfinderAPI.studentjobfinder.Repository.*;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	StudentService studentService;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	OfferRepository offerRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	FileService fileService;
	@Autowired
	ImageRepositroy imageRepositroy;
	@Autowired
	EmailService emailService;


	//Register, Update & Delete company

	public String register(String email, String password , Role role, String name , String creationDate, String  sector,
			String description , String website , Address address,String phoneNumber, List<Offer> offers , AccountStatus accountStatus  ) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern pattern = Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(email);
		if (matcher.matches() && !isEmailUsed(email) && !isEmailUsedS(email) && !isEmailUsedA(email) ) {
			if( role == Role.COMPANY) {
				Company newCompany = new Company(email, password, role,name,creationDate,sector,description , website,address,phoneNumber, offers ,accountStatus);
				newCompany.setPassword(new BCryptPasswordEncoder().encode(newCompany.getPassword()));
				companyRepository.save(newCompany);
				emailService.sendNewMail(newCompany.getEmail(),newCompany.getVerificationCode(),newCompany.getName() + " corporation");
				return "Company created successfully";
			}
		}
		if (matcher.matches()) {
			if(isEmailUsedS(email) || isEmailUsed(email) || isEmailUsedA(email))  {
				return "The email you entered is already linked to an account";
			}
		}
		return "Please enter a valid email address";
	}

	public Company update(Company company ,String token )  {
		Company company1 = getCompanyFromToken(token);
		if (company1 != null) {
			company1.setName(company.getName());
			company1.setDescription(company.getDescription());
			company1.setAddress(company.getAddress());
			company1.setCreationDate(company.getCreationDate());
			company1.setWebsite(company.getWebsite());
			company1.setLinkedin(company.getLinkedin());
			company1.setSector(company.getSector());
			company1.setPhoneNumber(company.getPhoneNumber());
			company1.setAverageEmployeeAge(company.getAverageEmployeeAge());
			company1.setNumberOfEmployees(company.getNumberOfEmployees());
			company1.setPresentation(company.getPresentation());
			Image logo = company.getLogo();
			Image banner = company.getBanner();
			imageRepositroy.save(logo);
			company1.setLogo(logo);
			imageRepositroy.save(banner);
			company1.setBanner(banner);
			companyRepository.save(company1);
		}
		return company1;
	}

	public String deleteCompany( String token ) {
		Company company = getCompanyFromToken(token);
		companyRepository.delete(company);
		return "Your account has been deleted";
	}

	//Offer creation , Update , removal and status changing
	public void addOffer(Offer offer, Company company) {
		List <Offer> offers = company.getOffers();
		offers.add(offer);
		company.setOffers(offers);
	}

	public void createOffer(String token, Offer offer) {
		System.out.println("ok");
		Company company = getCompanyFromToken(token);
		offer.setStatus(OfferStatus.OPEN);
		offer.setOfferCandidacies(new ArrayList<>());
		offer.setCompanyId(company.getId());
		addOffer(offer, company);
		offerRepository.save(offer);
		companyRepository.save(company);

	}

	public Offer updateOffer( String token,Offer offer,String id  ) {
		Company company = getCompanyFromToken(token);
		Optional<Offer> e = offerRepository.findById(id);
		Offer existingOffer = e.get();
		for(int i=0 ; i< company.getOffers().size(); i++) {
			if(existingOffer.getId().equals(company.getOffers().get(i).getId())) {
				existingOffer.setTitle(offer.getTitle());
				existingOffer.setRemoteWork(offer.isRemoteWork());
				existingOffer.setSalary(offer.getSalary());
				existingOffer.setLanguage(offer.getLanguage());
				existingOffer.setSearchedProfile(offer.getSearchedProfile());
				existingOffer.setSector(offer.getSector());
				existingOffer.setStartDate(offer.getStartDate());
				existingOffer.setEndDate(offer.getEndDate());
				existingOffer.setStatus(offer.getStatus());
				existingOffer.setQuestions(offer.getQuestions());
				existingOffer.setContext(offer.getContext());
				existingOffer.setAddress(offer.getAddress());
				existingOffer.setNbrPlaces(offer.getNbrPlaces());
				existingOffer.setMission(offer.getMission());
				existingOffer.setContract(offer.getContract());
				existingOffer.setLevel(offer.getLevel());
				addOffer(existingOffer, company);
				offerRepository.save(existingOffer);
				return existingOffer;
			}
		}
		return  null;
	}

	public  List<Offer> myOffers(String token ) {
		Company company = getCompanyFromToken(token);
		List<Offer> offers =  company.getOffers();

		return  offers;
	}



	public  List<Offer> companyOpenOffers(String id) {
		List<Offer> offers = offerRepository.findOffersByCompanyId(id);
		List<Offer> companyOffers = new ArrayList<>();
		for(int i=0; i<offers.size(); i++) {

			if(offers.get(i).getStatus() == OfferStatus.OPEN) {
				companyOffers.add(offers.get(i));
			}
		}
		return  companyOffers;
	}


	public String offerClosed(String token , Offer offer) {
		Company company = getCompanyFromToken(token);
		List<Offer> offers = company.getOffers();
		Optional<Offer> e = offerRepository.findById(offer.getId());
		Offer existingOffer = e.get();
		for(int i=0 ; i<offers.size(); i++) {
			if(existingOffer.getId().equals(company.getOffers().get(i).getId())) {
				existingOffer.setStatus(OfferStatus.CLOSED);

				offerRepository.save(existingOffer);
				return "Offer is closed";
			}
		}
		return  null;


	}

	public String offerDone(String token , Offer offer) {
		Company company = getCompanyFromToken(token);
		List<Offer> offers = company.getOffers();
		Optional<Offer> e = offerRepository.findById(offer.getId());
		Offer existingOffer = e.get();
		for(int i=0 ; i< offers.size(); i++) {
			if(existingOffer.getId().equals(company.getOffers().get(i).getId())) {
				existingOffer.setStatus(OfferStatus.DONE);

				offerRepository.save(existingOffer);
				return "Offer is closed";
			}
		}
		return  null;
	}

	public String offerOpen(String token , Offer offer) {
		Company company = getCompanyFromToken(token);
		List<Offer> offers = company.getOffers();
		Optional<Offer> e = offerRepository.findById(offer.getId());
		Offer existingOffer = e.get();
		for(int i=0 ; i< offers.size(); i++) {
			if(existingOffer.getId().equals(company.getOffers().get(i).getId())) {
				existingOffer.setStatus(OfferStatus.OPEN);

				offerRepository.save(existingOffer);
				return "Offer is Open";
			}
		}
		return  null;
	}

	//filters
	public List<Offer> getFilteredOffers(FilteredOfferRequest filteredOfferRequest) {
		List <Offer> offers = offerRepository.findAll();
		List <Offer> filteredOffersClone = new ArrayList<Offer>(offers);
		if(filteredOfferRequest.getContractType()!=null ) {
			filteredOffersClone = filteredOffersClone.stream().filter(offer -> offer.getContract().equals(filteredOfferRequest.getContractType())).toList();
		}
		if(filteredOfferRequest.getLevel()!=null && filteredOfferRequest.getLevel().length()>0) {
			filteredOffersClone = filteredOffersClone.stream().filter(offer -> offer.getLevel().toLowerCase().equals(filteredOfferRequest.getLevel().toLowerCase())).toList();
		}
		if(filteredOfferRequest.getCity()!=null && filteredOfferRequest.getCity().length()>0) {
			filteredOffersClone = filteredOffersClone.stream().filter(offer -> offer.getAddress().getCity().toLowerCase().equals(filteredOfferRequest.getCity().toLowerCase())).toList();
		}
		if(filteredOfferRequest.getKeyword()!=null) {
			filteredOffersClone = filteredOffersClone.stream().filter(Offer -> Offer.getTitle()!=null && Offer.getTitle().toLowerCase().contains(filteredOfferRequest.getKeyword().toLowerCase())).toList();
		}
		return filteredOffersClone;
	}

	public List<Company> getFilteredCompanies(FilteredCompanyRequest filteredCompanyRequest) {
		List<Company> companies = companyRepository.findAll();
		List<Company> filteredCompaniesClone = new ArrayList<Company>(companies);
		if(filteredCompanyRequest.getCity()!=null && filteredCompanyRequest.getCity().length()>0) {
			filteredCompaniesClone = filteredCompaniesClone.stream().filter(Company -> Company.getAddress()!= null && Company.getAddress().getCity()!= null && Company.getAddress().getCity().toLowerCase().equals(filteredCompanyRequest.getCity().toLowerCase())).toList();
		}
		if(filteredCompanyRequest.getSector()!=null && filteredCompanyRequest.getSector().length()>0) {
			filteredCompaniesClone = filteredCompaniesClone.stream().filter(Company -> Company.getSector()!= null && Company.getSector().equals(filteredCompanyRequest.getSector())).toList();
		}
		if(filteredCompanyRequest.getKeyword()!=null && filteredCompanyRequest.getKeyword().length()>0) {
			filteredCompaniesClone = filteredCompaniesClone.stream().filter(Company -> Company.getName()!=null && Company.getName().toLowerCase().contains(filteredCompanyRequest.getKeyword().toLowerCase())).toList();
		}
		return filteredCompaniesClone;
	}

	// Credentials , Token and password update
	public String updatePassword(PasswordChangeRequest passwordChangeRequest, String token) {
		Company company = getCompanyFromToken(token);
		if (checkCredentials(company.getEmail(), passwordChangeRequest.getOldPassword())) {
			company.setPassword(new BCryptPasswordEncoder().encode(passwordChangeRequest.getNewPassword()));
			companyRepository.save(company);
			return"password changed";
		}
		return"incorrect password";
	}

	public Company getCompanyFromToken(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		int endEmail = payload.indexOf(",");
		String companyEmail = payload.substring(8, endEmail-1);
		return companyRepository.findCompanyByEmail(companyEmail);
	}

	public boolean isEmailUsed(String email) {
		List<Company> companies = companyRepository.findAll();
		for (int i=0; i<companies.size(); i++ ) {
			if (companies.get(i).getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	public boolean isEmailUsedS(String email) {
		List <Student> students = studentRepository.findAll();
		for (int i=0; i<students.size(); i++ ) {
			if (students.get(i).getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
	public boolean isEmailUsedA(String email) {
		List <Admin> admins = adminRepository.findAll();
		for (int i=0; i<admins.size(); i++ ) {
			if (admins.get(i).getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkCredentials(String email, String password) {
		Account account = companyRepository.findCompanyByEmail(email);
		BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
		if(encoder.matches(password, account.getPassword())){
			return true;
		}
		return false;
	}



	public String verifyCompany(String code,String email) {
		Company company = companyRepository.findCompanyByEmail(email);
		if(company.getVerificationCode().equals(code)) {
			if((new Date (System.currentTimeMillis()).getTime()-company.getVerificationCodeExpirationDate().getTime())<=86400000) {
				company.setEnabled(true);
				companyRepository.save(company);
				return "Verified successfully";
			}
			else {
				resetVerificationCode(company);
				return "Verification code expired. A new one has been sent to "+company.getEmail();
			}
		}
		throw new ExceptionInInitializerError("blob");
	}

	public void resetVerificationCode(Company company) {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		company.setVerificationCode( String.format("%06d", number));
		company.setVerificationCodeExpirationDate(new Date (System.currentTimeMillis()));
		companyRepository.save(company);
		emailService.sendNewMail(company.getEmail(),company.getVerificationCode(),company.getName());
	}

	public Student reportStudent(Company company, Student student, String message) {
		student.getReports().setReported(true);
		student.getReports().getMessages().add(message);
		student.getReports().getReportersId().add(company.getId());
		studentRepository.save(student);
		return student;
	}

}


