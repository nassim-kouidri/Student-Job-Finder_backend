package studentjobfinderAPI.studentjobfinder.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import studentjobfinderAPI.studentjobfinder.Model.*;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends MongoRepository<Offer, String> {

    @Query(value = "{ 'companyId' : ?0 }")
    List<Offer> findOffersByCompanyId(String companyId);
    
    @Query(value = "{ 'id' : ?0 }")
    Optional<Offer> findById(String id);

    @Query(value = "{ 'status' : ?0 }")
    List<Offer> findOffersByOfferStatus(OfferStatus offerStatus);
    
    @Query(value = "{ 'reports.isReported' : ?0 }")
    List<Offer> findReportedOffers(boolean isReported);
    
}