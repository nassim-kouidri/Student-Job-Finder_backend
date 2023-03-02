package studentjobfinderAPI.studentjobfinder.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import studentjobfinderAPI.studentjobfinder.Model.Account;
import studentjobfinderAPI.studentjobfinder.Model.Admin;
import studentjobfinderAPI.studentjobfinder.Model.Company;

public interface AdminRepository extends MongoRepository<Admin, String> {

    @Query("{email: '?0'}")
    Admin findAdminByEmail(String email);

}
