package studentjobfinderAPI.studentjobfinder.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import studentjobfinderAPI.studentjobfinder.Model.ChatRoom;

public interface ChatRepository  extends MongoRepository<ChatRoom,String>{
	
	 @Query(value = "{ 'companyId' : ?0, 'studentId' : ?1 }")
     Optional<ChatRoom> findByCompanyIdAndStudentId(String companyId, String studentId);

	 @Query(value = "{ 'studentId' : ?0 }")
     List<ChatRoom> findByStudentid(String studentId);
	 
	 @Query(value = "{ 'companyId' : ?0 }")
     List<ChatRoom> findByCompanyid(String companyId);
}

