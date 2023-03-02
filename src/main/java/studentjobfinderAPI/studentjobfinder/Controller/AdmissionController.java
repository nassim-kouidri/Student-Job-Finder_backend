package studentjobfinderAPI.studentjobfinder.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import studentjobfinderAPI.studentjobfinder.Model.*;
import studentjobfinderAPI.studentjobfinder.Payload.Request.AdmissionRequest;
import studentjobfinderAPI.studentjobfinder.Repository.AdmissionRepository;
import studentjobfinderAPI.studentjobfinder.Service.AdmissionService;
import studentjobfinderAPI.studentjobfinder.Service.CompanyService;
import studentjobfinderAPI.studentjobfinder.Service.StudentService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AdmissionController {
    @Autowired
    AdmissionService admissionService;
    @Autowired
    AdmissionRepository admissionRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    CompanyService companyService;


    @PostMapping("/student/offer/admission/{id}")
    public String candidate (@RequestHeader(HttpHeaders.AUTHORIZATION) String token , @RequestBody Admission admission , @PathVariable("id") String offerId , Candidacy candidacy , OfferCandidacy offerCandidacy) {
      return  admissionService.candidate(token , admission,offerId,candidacy,offerCandidacy);
    }


    @GetMapping("/student/admission/{id}")
    public  Admission admission(@PathVariable("id") String admissionId) {
        return admissionRepository.findAdmissionById(admissionId);
    }



        @GetMapping("/student/admissions")
    public List<Admission> admissions(@RequestHeader(HttpHeaders.AUTHORIZATION) String token ) {
        return admissionService.admissionList(token );
    }

    @GetMapping("/company/admission/{id}")
    public  Admission admissionComp(@PathVariable("id") String admissionId) {
        return admissionRepository.findAdmissionById(admissionId);
    }

    @GetMapping("/company/offer/admissions/{id}")
    public List<Admission> admissions(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("id") Offer offer ) {
        return admissionService.admissionListComp(token,offer );
    }


    @PostMapping("/company/admissionInProgress/{id}")
    public  String admissionInProgress( @RequestHeader(HttpHeaders.AUTHORIZATION) String token ,@PathVariable("id") Admission admission ) {
         Company company = companyService.getCompanyFromToken(token);
        return admissionService.admissionProgress(token,admission);
    }

    @PostMapping("/company/admissionAccepted/{id}")
    public  String admissionAccepted( @RequestHeader(HttpHeaders.AUTHORIZATION) String token ,@PathVariable("id") Admission admission ) {
        Company company = companyService.getCompanyFromToken(token);
        return admissionService.admissionAccepted(token,admission);
    }

    @PostMapping("/company/admissionRejected/{id}")
    public  String admissionRejected( @RequestHeader(HttpHeaders.AUTHORIZATION) String token ,@PathVariable("id") Admission admission ) {
        Company company = companyService.getCompanyFromToken(token);
        return admissionService.admissionRejected(token,admission);
    }


}






