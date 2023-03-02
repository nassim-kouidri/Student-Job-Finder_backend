package studentjobfinderAPI.studentjobfinder.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import studentjobfinderAPI.studentjobfinder.Model.*;

import java.util.List;
import java.util.Optional;

public interface AdmissionRepository extends MongoRepository<Admission,String> {

        @Query(value = "{ 'student.id' : ?0 }")
        List<Admission> findAdmissionByStudentId(String studentId);
        @Query(value = "{ 'offer.id' : ?0 }")
        List<Admission> findAdmissionByOfferId(String offerId);

        @Query(value = "{ 'id' : ?0 }")
        Admission findAdmissionById(String id);

        @Query(value = "{ 'offer.id' : ?0, 'student.id' : ?1 }")
        Optional<Admission> findByOfferIdAndStudentId(String offerId, String studentId);



}
