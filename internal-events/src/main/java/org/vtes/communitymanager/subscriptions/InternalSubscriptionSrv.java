package org.vtes.communitymanager.subscriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vtes.communitymanager.subscriptions.SubscriptionDocument;
import org.vtes.communitymanager.subscriptions.SubscriptionReadRepository;
import org.vtes.communitymanager.subscriptions.SubscriptionRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternalSubscriptionSrv {
    private final SubscriptionReadRepository subscriptionReadRepository;
    private final SubscriptionRepository subscriptionRepository;
    public boolean subscribeToFutureEvents(final String chatId) {
        final String id = UUID.randomUUID().toString();
        final SubscriptionDocument subscription = new SubscriptionDocument(id, chatId);
        this.subscriptionRepository.save(subscription);
        return true;
    }
}
