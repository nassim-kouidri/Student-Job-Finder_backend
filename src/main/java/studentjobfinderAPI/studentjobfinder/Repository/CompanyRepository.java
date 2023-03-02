package studentjobfinderAPI.studentjobfinder.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import studentjobfinderAPI.studentjobfinder.Model.AccountStatus;
import studentjobfinderAPI.studentjobfinder.Model.Admission;
import studentjobfinderAPI.studentjobfinder.Model.Company;
import studentjobfinderAPI.studentjobfinder.Model.Offer;
import studentjobfinderAPI.studentjobfinder.Model.Student;

import javax.servlet.SingleThreadModel;
import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {

	@Query("{email: '?0'}")
	Company findCompanyByEmail(String email);
	
	@Query(value = "{ 'id' : ?0 }")
	Company findCompanyById(String id);

	@Query(value = "{ 'accountStatus' : ?0 }")
	List<Company> findCompanyByAccountStatus(AccountStatus accountStatus);
	
	@Query(value = "{ 'reports.isReported' : ?0 }")
    List<Company> findReportedCompanys(boolean isReported);

}
