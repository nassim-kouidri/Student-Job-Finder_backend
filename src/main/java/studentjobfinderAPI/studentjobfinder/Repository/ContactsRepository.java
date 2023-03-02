package studentjobfinderAPI.studentjobfinder.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import studentjobfinderAPI.studentjobfinder.Model.Admin;
import studentjobfinderAPI.studentjobfinder.Model.Contact;




public interface ContactsRepository extends MongoRepository<Contact, String> {

    @Query("{email: '?0'}")
    Contact findContactByEmail(String email);

}



