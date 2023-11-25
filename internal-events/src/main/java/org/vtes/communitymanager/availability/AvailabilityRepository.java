package org.vtes.communitymanager.availability;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends MongoRepository<AvailabilityDocument, String> {
}
