package studentjobfinderAPI.studentjobfinder.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import studentjobfinderAPI.studentjobfinder.Model.Image;

public interface ImageRepositroy extends MongoRepository <Image,String> {
}
