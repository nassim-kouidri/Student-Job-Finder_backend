package studentjobfinderAPI.studentjobfinder.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import studentjobfinderAPI.studentjobfinder.Model.*;
import studentjobfinderAPI.studentjobfinder.Payload.Request.PasswordChangeRequest;
import studentjobfinderAPI.studentjobfinder.Repository.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class AdminService {


	@Autowired
	AdminRepository adminRepository;
	@Autowired
	StudentRepository studentRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	ImageRepositroy imageRepositroy;

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	ContactsRepository contactsRepository;

	public String register(String email, String password, int id , Role role , String name , String fname ,AccountStatus accountStatus ) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern pattern = Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(email);
		if (matcher.matches() && !isEmailUsed(email) && !isEmailUsedS(email) && !isEmailUsedA(email) ) {
			if( role == Role.ADMIN) {
				Admin newAdmin = new Admin(email, password , role , name , fname,accountStatus );
				newAdmin.setPassword(new BCryptPasswordEncoder().encode(newAdmin.getPassword()));
				adminRepository.save(newAdmin);
				return "Admin created successfully";
			}
		}
		if (matcher.matches()) {
			if(isEmailUsedS(email) || isEmailUsed(email) || isEmailUsedA(email) ) {
				return "The email you entered is already linked to an account";
			}
		}
		return "Please enter a valid email address";
	}


	public Admin update(Admin admin , String token) {
		Admin admin1 = getAdminByToken(token);
		if (admin1 != null) {
			admin1.setProfilePic(admin.getProfilePic());
			Image image = (admin.getProfilePic()) ;
			admin1.setProfilePic(image);
			imageRepositroy.save(image);
			adminRepository.save(admin1);
		}
		return  admin1;
	}

	public String updatePassword(PasswordChangeRequest passwordChangeRequest, String token) {
		Admin admin = getAdminByToken(token);
		if (checkCredentials(admin.getEmail(), passwordChangeRequest.getOldPassword())) {
			admin.setPassword(new BCryptPasswordEncoder().encode(passwordChangeRequest.getNewPassword()));
			adminRepository.save(admin);
			return"password changed";
		}
		return"incorrect password";
	}

	public String deleteAdmin( String token ) {
		Admin admin = getAdminByToken(token);
		adminRepository.delete(admin);
		return "Your account has been deleted";
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
	public Admin getAdminByToken(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		int endEmail = payload.indexOf(",");
		String adminEmail = payload.substring(8, endEmail-1);
		return adminRepository.findAdminByEmail(adminEmail);
	}

	public boolean checkCredentials(String email, String password) {
		Account account = adminRepository.findAdminByEmail(email);
		if(account.getPassword().equals(password)){
			return true;
		}
		return false;
	}

	public String banCompany(String token, Company company) {
		Admin admin = getAdminByToken(token);
		Company company1 = companyRepository.findCompanyById(company.getId());
		if (company1.getAccountStatus() == AccountStatus.BANNED) {
			company1.setAccountStatus(AccountStatus.ACTIVE);
			companyRepository.save(company1);
			return  "Company is unbanned";
		}
		if (company1.getAccountStatus() == AccountStatus.ACTIVE) {
			company1.setAccountStatus(AccountStatus.BANNED);
			companyRepository.save(company1);
			return  "Company is banned";
		}
		return  null;
	}

	public String banStudent(String token, Student student) {
		Admin admin = getAdminByToken(token);
		Student student1 = studentRepository.findStudentById(student.getId());
		if (student1.getAccountStatus() == AccountStatus.BANNED) {
			student1.setAccountStatus(AccountStatus.ACTIVE);
			studentRepository.save(student1);
			return  "Student is unbanned";
		}
		if (student1.getAccountStatus() == AccountStatus.ACTIVE) {
			student1.setAccountStatus(AccountStatus.BANNED);
			studentRepository.save(student1);
			return  "Student is banned";
		}
		return  null;
	}

	public String offerBanned(String token , Offer offer  ) {
		Admin admin = getAdminByToken(token);
		offer.setStatus(OfferStatus.BANNED);
		offerRepository.save(offer);
		return "You have banned the offer : " + offer.getTitle()  ;
	}


	public  List<Offer> companyBannedOffers(String id) {
		List<Offer> offers = offerRepository.findOffersByCompanyId(id);
		List<Offer> companyOffers = new ArrayList<>();
		for(int i=0; i<offers.size(); i++) {

			if(offers.get(i).getStatus() == OfferStatus.BANNED) {
				companyOffers.add(offers.get(i));
			}
		}
		return  companyOffers;
	}


	public Contact contactSite (Contact contact) {
		contact.setContactSiteStatus(ContactSiteStatus.OPEN);
		contactsRepository.save(contact);

		return contact;
	}
	public String contactSiteStatus (Contact contact) {
          if(contact.getContactSiteStatus().equals(ContactSiteStatus.OPEN)) {
			  contact.setContactSiteStatus(ContactSiteStatus.ARCHIVED);
			  contactsRepository.save(contact);

			  return "Contact Message is now archived";

		  }
			  contact.setContactSiteStatus(ContactSiteStatus.OPEN);

		contactsRepository.save(contact);

		return "Contact Message is now unarchived";
	}

}