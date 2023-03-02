package studentjobfinderAPI.studentjobfinder.Controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import studentjobfinderAPI.studentjobfinder.Configuration.JwtTokenUtil;
import studentjobfinderAPI.studentjobfinder.Model.*;
import studentjobfinderAPI.studentjobfinder.Payload.Request.EmailRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.LoginRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.PasswordChangeRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.RegisterRequest;

import studentjobfinderAPI.studentjobfinder.Payload.Response.TokenResponse;
import studentjobfinderAPI.studentjobfinder.Repository.AdminRepository;
import studentjobfinderAPI.studentjobfinder.Repository.ChatRepository;
import studentjobfinderAPI.studentjobfinder.Repository.CompanyRepository;
import studentjobfinderAPI.studentjobfinder.Repository.OfferRepository;
import studentjobfinderAPI.studentjobfinder.Repository.StudentRepository;
import studentjobfinderAPI.studentjobfinder.Service.ChatService;
import studentjobfinderAPI.studentjobfinder.Service.FileService;
import studentjobfinderAPI.studentjobfinder.Service.StudentService;
@RestController
@RequestMapping("/api")
public class StudentController {


	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	StudentService studentService;
	@Autowired
	FileService fileService;
	TokenResponse tokenResponse;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired 
	OfferRepository offerRepository;
	@Autowired
	ChatRepository chatRepository;
	@Autowired 
	ChatService chatService;
	
	@PostMapping("/public/register/student")
	public String register(@RequestBody RegisterRequest registerRequest ) {
		return studentService.register(registerRequest.getEmail(), registerRequest.getPassword(), Role.STUDENT ,
				registerRequest.getName(),registerRequest.getFname(),registerRequest.getBirthDate(),registerRequest.getPhoneNumber() , new ArrayList<>(),AccountStatus.ACTIVE	 );
	}

	@PostMapping("/public/student/verify/{code}")
	public String verifyStudent(@RequestBody EmailRequest email ,@PathVariable("code") String code) {
		return studentService.verifyStudent(code, email.getEmail());
	}
	
	@PostMapping("/public/student/verify/reset")
	public void resetVerifyCode(@RequestBody EmailRequest email) {
		studentService.resetVerificationCode(studentRepository.findStudentByEmail(email.getEmail()));
	}
	

	@GetMapping("/getStudent")
	public Student getStudent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		return studentService.getStudentFromToken(token);
	}

	@PutMapping("/student/changePassword")
	public String updatePassword(@RequestBody PasswordChangeRequest passwordChangeRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String token ) {
		return studentService.updatePassword(passwordChangeRequest, token);
	}


	@PutMapping("/student/update" )
	public Student update(@RequestBody Student student, @RequestHeader(HttpHeaders.AUTHORIZATION) String token )  {
		studentService.update(student,token );
		return student;
	}
	/*@PutMapping(value = "/updateStudentPhoto" , consumes = "multipart/form-data")
	public void update(@RequestPart("photo") MultipartFile file,
					   @RequestHeader(HttpHeaders.AUTHORIZATION) String token ) throws  IOException  {
		studentService.updatePhoto(file,token );
	}*/
	@DeleteMapping(value = "/student/delete")
	public String deleteStudent(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String token )  {
		return studentService.deleteStudent(token );
	}
	/*@GetMapping("/public/download/{id}")
	public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
		LoadFile loadFile = fileService.downloadFile(id);

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(loadFile.getFileType() ))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
				.body(new ByteArrayResource(loadFile.getFile()));
	}*/
	@PostMapping("/public/login")
	public studentjobfinderAPI.studentjobfinder.Payload.Response.TokenResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws Exception {
		Role userRole = null;
		Student student = null;
		Company company = null;
		Admin admin= null;
		List<Student> students = studentRepository.findAll();
		List<Company> companies = companyRepository.findAll();
		List <Admin> admins = adminRepository.findAll();
		BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
		for (int i=0;i<students.size();i++) {
			if(students.get(i).getEmail().equals(loginRequest.getEmail()) ) {
				userRole= Role.STUDENT;
				student = students.get(i);
			}
		}
		for (int i=0;i<companies.size();i++) {
			if(companies.get(i).getEmail().equals(loginRequest.getEmail())  ) {
				userRole= Role.COMPANY;
				company = companies.get(i);

			}
		}
		for (int i=0;i<admins.size();i++) {
			if(admins.get(i).getEmail().equals(loginRequest.getEmail())) {
				userRole= Role.ADMIN;
				admin = admins.get(i);
			}
		}
		if(userRole==Role.STUDENT){
			if(!student.isEnabled()) {
throw  new Exception("");	}


			if(encoder.matches(loginRequest.getPassword(), studentRepository.findStudentByEmail(loginRequest.getEmail()).getPassword())) {
				if(student.getAccountStatus() == AccountStatus.BANNED) {
					throw new  DisabledException("");
				}
				String token = jwtTokenUtil.generateAccessToken(studentRepository.findStudentByEmail(loginRequest.getEmail()));
				logger.info("Token is : " + token);
				Student studentClone = student;
				TokenResponse tokenResponse=  new studentjobfinderAPI.studentjobfinder.Payload.Response.TokenResponse ( token, userRole,studentClone);
				tokenResponse.setFavOffers(student.getFavOffersId());
				studentClone.setFavOffersId(null);
				return tokenResponse;
			}
		}
		if(userRole==Role.COMPANY){
			if(!company.isEnabled()) {
				throw  new Exception("");
			}
			if(encoder.matches(loginRequest.getPassword(), companyRepository.findCompanyByEmail(loginRequest.getEmail()).getPassword())) {
				if(company.getAccountStatus() == AccountStatus.BANNED) {
					throw new  DisabledException("");
				}
				String token = jwtTokenUtil.generateAccessToken(companyRepository.findCompanyByEmail(loginRequest.getEmail()));
				logger.info("Token is : " + token);
				Company companyClone = company;
				companyClone.setOffers(null);
				return new TokenResponse( token, userRole,companyClone);
			}
		}
		if(userRole==Role.ADMIN){
			if(encoder.matches(loginRequest.getPassword(), adminRepository.findAdminByEmail(loginRequest.getEmail()).getPassword())) {
				String token = jwtTokenUtil.generateAccessToken(adminRepository.findAdminByEmail(loginRequest.getEmail()));
				logger.info("Token is : " + token);
				return  new studentjobfinderAPI.studentjobfinder.Payload.Response.TokenResponse (token, userRole , admin);
			}
		}
		throw new BadCredentialsException("");

	}

	@GetMapping("/public/students")

	public List<Student> getStudents() {
		return studentRepository.findStudentByAccountStatus(AccountStatus.ACTIVE);
	}
	@GetMapping("/public/student/{id}")
	public Student getStudentFromId( @PathVariable("id") String studentId) {
		return studentRepository.findStudentById(studentId);
	}

	private void tokenResponse(String token, Role userRole , Student student) {
		// TODO Auto-generated method stub
		
	}

	@PostMapping("/student/offer/addFavorite/{id}")
	public String addFavOffer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") String offerId ) {
		 return  studentService.addFavOffer(token, offerId );

	}

	/*@DeleteMapping("/student/offer/deleteFavorite/{id}")
	public String deleteFavOffer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("id") String offerId ) {
		return studentService.removeFavOffer(token, offerId );

	}*/

	@GetMapping("/student/myFavOffers")
	public List<Offer> getOffers(@RequestHeader(HttpHeaders.AUTHORIZATION) String token ) {
		return studentService.myFavOffers(token);
	}
	
	@PostMapping("/student/reportOffer/{id}")
	public Offer reportOffer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody EmailRequest message, @PathVariable("id") String offerId ) {
		return studentService.reportOffer(studentService.getStudentFromToken(token), offerRepository.findById(offerId).get(), message.getEmail());
	}
	
	@PostMapping("student/reportCompany/{id}")
	public Company reportCompany (@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody EmailRequest message, @PathVariable("id") String companyId ) {
		return studentService.reportCompany(studentService.getStudentFromToken(token), companyRepository.findCompanyById(companyId), message.getEmail());
	}
	
	@PostMapping("student/newChat/{companyId}")
	public ChatRoom createChatroom(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("companyId") String companyId) {
		if(chatRepository.findByCompanyIdAndStudentId(companyId, studentService.getStudentFromToken(token).getId()).isEmpty()) {
			return chatService.createChatRoom(companyId, studentService.getStudentFromToken(token).getId());
		}
		return chatRepository.findByCompanyIdAndStudentId(companyId, studentService.getStudentFromToken(token).getId()).get();
	}
	
	@GetMapping("student/chatrooms")
	public List<ChatRoom> getChatRoomsFromStudent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
		return chatRepository.findByStudentid(studentService.getStudentFromToken(token).getId());
	}
	
	@GetMapping("/student/chatroom/messages/{chatroomId}")
	public List<OutputMessage> getMessagesFromChatRoom(@PathVariable("chatroomId") String chatroomId){
		return chatRepository.findById(chatroomId).get().getMessages();
	}


}
