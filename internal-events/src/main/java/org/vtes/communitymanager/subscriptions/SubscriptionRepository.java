package org.vtes.communitymanager.subscriptions;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends MongoRepository<SubscriptionDocument, String> {
}
