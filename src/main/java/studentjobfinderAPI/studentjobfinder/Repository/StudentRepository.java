package studentjobfinderAPI.studentjobfinder.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import studentjobfinderAPI.studentjobfinder.Model.AccountStatus;
import studentjobfinderAPI.studentjobfinder.Model.Student;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student,String>{

    @Query("{email: '?0'}")
   Student findStudentByEmail(String email);

    @Query(value = "{ 'id' : ?0 }")
    Student findStudentById(String id);


    @Query(value = "{ 'accountStatus' : ?0 }")
    List<Student> findStudentByAccountStatus(AccountStatus accountStatus);
    
    @Query(value = "{ 'reports.isReported' : ?0 }")
    List<Student> findReportedStudents(boolean isReported);

}
