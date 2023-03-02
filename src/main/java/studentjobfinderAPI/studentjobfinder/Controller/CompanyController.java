package studentjobfinderAPI.studentjobfinder.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import studentjobfinderAPI.studentjobfinder.Model.*;
import studentjobfinderAPI.studentjobfinder.Payload.Request.EmailRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.FilteredCompanyRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.FilteredOfferRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.PasswordChangeRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.RegisterRequest;
import studentjobfinderAPI.studentjobfinder.Repository.ChatRepository;
import studentjobfinderAPI.studentjobfinder.Repository.CompanyRepository;
import studentjobfinderAPI.studentjobfinder.Repository.OfferRepository;
import studentjobfinderAPI.studentjobfinder.Repository.StudentRepository;
import studentjobfinderAPI.studentjobfinder.Service.StudentService;
import studentjobfinderAPI.studentjobfinder.Service.ChatService;
import studentjobfinderAPI.studentjobfinder.Service.CompanyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {
	@Autowired
	CompanyService companyService;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	StudentService studentService;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	ChatRepository chatRepository;
	@Autowired
	ChatService chatService;

	@PostMapping("public/register/company")

	public String registerCompany(@RequestBody RegisterRequest registerRequest ) {
		return companyService.register(registerRequest.getEmail(), registerRequest.getPassword(),
				Role.COMPANY, registerRequest.getName() , registerRequest.getCreationDate(), registerRequest.getSector()
				, registerRequest.getDescription(), registerRequest.getWebsite(), registerRequest.getAddress(),registerRequest.getPhoneNumber(), new ArrayList<>() , AccountStatus.ACTIVE);
	}

	@PostMapping("/public/company/verify/{code}")
	public String verifyCompany(@RequestBody EmailRequest email, @PathVariable("code") String code) {
		return companyService.verifyCompany(code, email.getEmail());
	}
	
	@PostMapping("/public/company/verify/reset")
	public void resetVerifyCode(@RequestBody EmailRequest email) {
		companyService.resetVerificationCode(companyRepository.findCompanyByEmail(email.getEmail()));
	}
	

	@PutMapping("/company/update" )
	public Company update(@RequestBody Company company,@RequestHeader(HttpHeaders.AUTHORIZATION) String token )  {
		companyService.update(company,token );
		return company;
	}

	@PutMapping("/company/changePassword")
	public String updatePassword(@RequestBody PasswordChangeRequest passwordChangeRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String token ) {
		return companyService.updatePassword(passwordChangeRequest, token);
	}


	@DeleteMapping(value = "/company/delete" )
	public String deleteCompany(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String token )  {
		return companyService.deleteCompany(token );

	}


	@GetMapping("/public/companies")

	public List<Company> getCompanies() {
		return companyRepository.findCompanyByAccountStatus(AccountStatus.ACTIVE);
	}
	@GetMapping("/public/company/{id}")
	public Company getCompanyFromId(@PathVariable("id") String companyId) {
		return  companyRepository.findCompanyById(companyId);
	}
	@PostMapping("/company/offers/create")
	public Offer createOffer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Offer offer) {
		companyService.createOffer(token, offer);
		return  offer;
	}
	@PutMapping("/company/offers/update/{id}")
	public Offer updateOffer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestBody Offer offer,@PathVariable("id") String id) {
		return companyService.updateOffer( token,offer,id );
	}


	@PostMapping("/public/filterOffers")
	public List<Offer> getFilteredOffers(@RequestBody FilteredOfferRequest filteredOfferRequest){
		return companyService.getFilteredOffers(filteredOfferRequest);
	}

	@PostMapping("/public/filterCompanies")
	public List<Company> getFilteredCompanys(@RequestBody FilteredCompanyRequest filteredCompanyRequest){
		return companyService.getFilteredCompanies(filteredCompanyRequest);
	}

	@PatchMapping("/company/offers/closedStatus/{id}")
	public  String offerClosed(  @RequestHeader(HttpHeaders.AUTHORIZATION) String token , @PathVariable("id") Offer offer ) {
		return companyService.offerClosed(token , offer);
	}
	@PatchMapping("/company/offers/doneStatus/{id}")
	public  String offerDone(  @RequestHeader(HttpHeaders.AUTHORIZATION) String token , @PathVariable("id") Offer offer ) {
		return companyService.offerDone(token , offer);
	}
	@PatchMapping("/company/offers/openStatus/{id}")
	public  String offerOpen(  @RequestHeader(HttpHeaders.AUTHORIZATION) String token , @PathVariable("id") Offer offer ) {
		return companyService.offerOpen(token , offer);
	}

	@GetMapping("/isEmailUsed")
	public boolean isEmailUsed(@RequestBody String email) {
		if(!companyService.isEmailUsed(email) && !companyService.isEmailUsedS(email) &&  !companyService.isEmailUsedA(email)) {
			return false;
		}
		return true;
	}
	@GetMapping("/public/offer/{id}")
	public Offer getOffer( @PathVariable("id") String offerId) {
		return offerRepository.findById(offerId).get();
	}

	@GetMapping("/company/myOffers")
	public List<Offer> getMyOffers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token ) {
		return companyService.myOffers(token);
	}


	@GetMapping("/public/offers")
	public List<Offer> getOffers() {
		return offerRepository.findOffersByOfferStatus(OfferStatus.OPEN);
	}

	@GetMapping("/public/company/getOpenOffers/{id}")
	public List<Offer> getOffers(@PathVariable("id") String companyId) {
		return companyService.companyOpenOffers(companyId);
	}

	@PostMapping("company/reportStudent/{id}")
	public Student reportStudent (@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody EmailRequest message, @PathVariable("id") String studentId ) {
		return companyService.reportStudent(companyService.getCompanyFromToken(token), studentRepository.findStudentById(studentId), message.getEmail());
	}

	@GetMapping("/getCompany")
	public Company getCompany(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		return companyService.getCompanyFromToken(token);
	}
	
	@PostMapping("company/newChat/{studentId}")
	public ChatRoom createChatroom(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("studentId") String studentId) {
		if(chatRepository.findByCompanyIdAndStudentId(companyService.getCompanyFromToken(token).getId(), studentId).isEmpty()) {
			return chatService.createChatRoom(companyService.getCompanyFromToken(token).getId(), studentId);
		}
		return chatRepository.findByCompanyIdAndStudentId(companyService.getCompanyFromToken(token).getId(), studentId).get();
	}
	
	
	@GetMapping("company/chatrooms")
	public List <ChatRoom> getChatRoomsFromCompany(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		return chatRepository.findByCompanyid(companyService.getCompanyFromToken(token).getId());
	}
	
	@GetMapping("/company/chatroom/messages/{chatroomId}")
	public List<OutputMessage> getMessagesFromChatRoom(@PathVariable("chatroomId") String chatroomId){
		return chatRepository.findById(chatroomId).get().getMessages();
	}

}
