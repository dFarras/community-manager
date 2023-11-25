package org.vtes.communitymanager.subscribe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vtes.communitymanager.subscriptions.InternalSubscriptionSrv;

@Service
@RequiredArgsConstructor
public class EventSubscriptionSrv {
    private final InternalSubscriptionSrv internalSubscriptionSrv;

    public void subscribeToFutureEvents(final String chatId) {
        this.internalSubscriptionSrv.subscribeToFutureEvents(chatId);
    }
}
