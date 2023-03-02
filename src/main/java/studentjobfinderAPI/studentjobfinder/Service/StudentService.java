package studentjobfinderAPI.studentjobfinder.Service;

import java.util.*;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import studentjobfinderAPI.studentjobfinder.Model.*;
import studentjobfinderAPI.studentjobfinder.Payload.Request.PasswordChangeRequest;
import studentjobfinderAPI.studentjobfinder.Repository.*;

@Service
public class StudentService {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	OfferRepository offerRepository;

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	AdmissionRepository admissionRepository;

	@Autowired
	AdminRepository adminRepository;
	@Autowired
	FileService fileService;
	@Autowired
	EmailService emailService;
	@Autowired
	ImageRepositroy imageRepositroy;

	public String register(String email, String password , Role role, String name, String fname, Date birthDate, String phoneNumber, List<Candidacy> candidacies,AccountStatus accountStatus ) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern pattern = Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(email);
		if (matcher.matches() && !isEmailUsed(email) && !isEmailUsedS(email) && !isEmailUsedA(email) ) {
			if( role == Role.STUDENT) {
				Student newStudent = new Student(email, password,role,name,fname,birthDate,phoneNumber,accountStatus);
				newStudent.setPassword(new BCryptPasswordEncoder().encode(newStudent.getPassword()));
				studentRepository.save(newStudent);
				emailService.sendNewMail(newStudent.getEmail(),newStudent.getVerificationCode(),newStudent.getName());
				return "Student created successfully";
			}
		}
		if (matcher.matches()) {
			if(isEmailUsedS(email) || isEmailUsed(email) || isEmailUsedA(email) ) {
				return "The email you entered is already linked to an account";
			}
		}
		return "Please enter a valid email address";
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
		Account account = studentRepository.findStudentByEmail(email);
		BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
		if(encoder.matches(password, account.getPassword())){
			return true;
		}
		return false;
	}

	public Student getStudentFromToken(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		int endEmail = payload.indexOf(",");
		String studentEmail = payload.substring(8, endEmail-1);
		return studentRepository.findStudentByEmail(studentEmail);
	}

	public Student update(Student student , String token) {
		Student student1 = getStudentFromToken(token);
		if (student1 != null) {
			student1.setPhoneNumber(student.getPhoneNumber());
			student1.setLevel(student.getLevel());
			student1.setArea(student.getArea());
			student1.setBirthDate(student.getBirthDate());
			student1.setBiography(student.getBiography());
			student1.setSector(student.getSector());
			student1.setContratType(student.getContratType());
			student1.setGithub(student.getGithub());
			student1.setLinkedin(student.getLinkedin());
			student1.setWebsite(student.getWebsite());
			student1.setDiplomaSector(student.getDiplomaSector());
			Image image = (student.getImage()) ;
			student1.setImage(image);
			imageRepositroy.save(image);
			studentRepository.save(student1);
		}

		return  student1;
	}

	/*public Student updatePhoto	( MultipartFile file , String token) throws  IOException {
		Student student1 = getStudentFromToken(token);
		if (student1 != null) {
			student1.setPhoto(fileService.addFile(file));
		}
		return studentRepository.save(student1);
	}*/

	public String deleteStudent( String token ) {
		Student student = getStudentFromToken(token);
		studentRepository.delete(student);

		return "Your account has been deleted";

	}
	public String updatePassword(PasswordChangeRequest passwordChangeRequest, String token) {
		Student student = getStudentFromToken(token);
		if (checkCredentials(student.getEmail(), passwordChangeRequest.getOldPassword())) {
			student.setPassword(new BCryptPasswordEncoder().encode(passwordChangeRequest.getNewPassword()));
			studentRepository.save(student);
			return"password changed";
		}
		return"incorrect password";
	}


	/*public String removeFavOffer(String token ,String offerId) {
		Student student = getStudentFromToken(token);
		List <String> offersId = student.getFavOffersId();
		for (int i = 0; i < student.getFavOffersId().size(); i++) {
			if (offersId.get(i).equals(offerId)) {
				offersId.remove(i);
				studentRepository.save(student);
				return "Favorite offer deleted";
			}
		}
		return "This offer is not your favorite yet";
	}*/


	public String addFavOffer(String token,String offerId ) {
		Student student = getStudentFromToken(token);
		List<String> offersId = student.getFavOffersId();
		for(int i=0; i<offersId.size();i++) {
		 if (offersId.get(i).equals(offerId)) {
				offersId.remove(i);
				studentRepository.save(student);
				return "Favorite offer deleted";
			}

		}

		student.getFavOffersId().add(offerId);
		studentRepository.save(student);
		return "Favorite offer Added";

	}

	public  List<Offer> myFavOffers(String token ) {
		Student student = getStudentFromToken(token);
		List<String> offersId =  student.getFavOffersId();
		List<Offer> offers = new ArrayList<>();
		for (String id : offersId) {
			Optional<Offer> offer = offerRepository.findById(id);
			if (offer.isPresent()) {
				offers.add(offer.get());
			}
		}
		return  offers;
	}

	public String verifyStudent(String code,String email) {
		Student student = studentRepository.findStudentByEmail(email);
		if(student.getVerificationCode().equals(code)) {
			System.out.println(new Date (System.currentTimeMillis()).getTime()-student.getVerificationCodeExpirationDate().getTime());
			if((new Date (System.currentTimeMillis()).getTime()-student.getVerificationCodeExpirationDate().getTime())<=86400000) {
				student.setEnabled(true);
				studentRepository.save(student);
				return "Verified successfully";
			}
			else {
				resetVerificationCode(student);
				return "Verification code expired. A new one has been sent to "+student.getEmail() ;
			}
		}
		throw new ExceptionInInitializerError("") ;


	}

	public void resetVerificationCode(Student student) {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		student.setVerificationCode( String.format("%06d", number));
		student.setVerificationCodeExpirationDate(new Date(System.currentTimeMillis()));
		studentRepository.save(student);
		emailService.sendNewMail(student.getEmail(),student.getVerificationCode(),student.getName());
	}

	public Offer reportOffer(Student student, Offer offer, String message) {
		offer.getReports().setReported(true);
		offer.getReports().getMessages().add(message);
		offer.getReports().getReportersId().add(student.getId());
		offerRepository.save(offer);
		return offer;
	}

	public Company reportCompany(Student student, Company company, String message) {
		company.getReports().setReported(true);
		company.getReports().getMessages().add(message);
		company.getReports().getReportersId().add(student.getId());
		companyRepository.save(company);
		return company;
	}
}
