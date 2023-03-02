package studentjobfinderAPI.studentjobfinder.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import studentjobfinderAPI.studentjobfinder.Model.*;
import studentjobfinderAPI.studentjobfinder.Payload.Request.PasswordChangeRequest;
import studentjobfinderAPI.studentjobfinder.Payload.Request.RegisterRequest;
import studentjobfinderAPI.studentjobfinder.Repository.*;
import studentjobfinderAPI.studentjobfinder.Service.AdminService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    StudentRepository studentRepository;


    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    ContactsRepository contactsRepository;

    @PostMapping("public/register/admin")

    public String registerAdmin(@RequestBody RegisterRequest registerRequest ) {
        return adminService.register(registerRequest.getEmail(), registerRequest.getPassword(),(int) adminRepository.count(), Role.ADMIN,registerRequest.getName(),registerRequest.getFname(), AccountStatus.ACTIVE
            );
    }
    @PutMapping("/admin/update" )
    public Admin update(@RequestBody Admin admin,
                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token )  {
        adminService.update(admin,token );
        return admin;
    }


    @PutMapping("/admin/changePassword/")
    public String updatePassword(@RequestBody PasswordChangeRequest passwordChangeRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String token ) {
        return adminService.updatePassword(passwordChangeRequest, token);
    }

    @DeleteMapping(value = "/admin/delete" )
    public String deleteAdmin(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token )  {
        return adminService.deleteAdmin(token );

    }

    @PatchMapping("/admin/updateCompanyStatus/{id}" )
    public String updateCompanyStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String token , @PathVariable("id") Company company )  {
       return adminService.banCompany(token,company);

    }

    @PatchMapping("/admin/updateStudentStatus/{id}" )
    public String updateStudentStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String token , @PathVariable("id") Student student ) {
        return adminService.banStudent(token, student);

    }


    @GetMapping("/admin/getBannedStudents")

    public List<Student> getBannedStudents() {
        return studentRepository.findStudentByAccountStatus(AccountStatus.BANNED);
    }

    //ACTIVE AND BANNED
    @GetMapping("/admin/getAllStudents")

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/admin/getBannedCompanies")

    public List<Company> getBannedCompanies() {
        return companyRepository.findCompanyByAccountStatus(AccountStatus.BANNED);
    }

    //ACTIVE AND BANNED
    @GetMapping("/admin/getAllCompanies")

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }



    @PatchMapping("/admin/offers/banStatus/{id}")
    public  String offerBanned( @RequestHeader(HttpHeaders.AUTHORIZATION) String token , @PathVariable("id") Offer offer ) {
        return adminService.offerBanned(token,offer  );
    }


    @GetMapping("/admin/getAllOffers")

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @GetMapping("/admin/getBannedOffers")

    public List<Offer> getBannedOffers() {
        return offerRepository.findOffersByOfferStatus(OfferStatus.BANNED);
    }

    @GetMapping("/admin/getReportedOffers")
    public List<Offer> getReportedOffers(){
    	return offerRepository.findReportedOffers(true);
    }
    @GetMapping("/admin/getReportedStudents")
    public List<Student> getReportedStudents(){
    	return studentRepository.findReportedStudents(true);
    }
    @GetMapping("/admin/getReportedCompanies")
    public List<Company> getReportedCompanies(){
    	return companyRepository.findReportedCompanys(true);
    }
    
    @GetMapping("/admin/company/getAllOffers/{id}")
    public List<Offer> getOffers(@PathVariable("id") String companyId) {
        return offerRepository.findOffersByCompanyId(companyId);
    }

    @GetMapping("/admin/company/getBannedOffers/{id}")
    public List<Offer> getCompanyOpenOffers(@PathVariable("id") String companyId) {
        return adminService.companyBannedOffers(companyId);
    }



    @PostMapping("/public/contactSite")
    public Contact contactSite(@RequestBody Contact contact) {
      return   adminService.contactSite(contact);
    }

    @GetMapping("/admin/getContactSite")
    public List<Contact> contactSite() {
        return   contactsRepository.findAll();
    }

    @PostMapping("/public/contactSiteStatus/{id}")
    public String contactSiteStatus(@PathVariable ("id") Contact contact) {
        return   adminService.contactSiteStatus(contact);
    }
}
