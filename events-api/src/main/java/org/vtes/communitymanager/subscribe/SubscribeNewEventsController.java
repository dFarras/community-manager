package org.vtes.communitymanager.subscribe;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events/subscribe")
public class SubscribeNewEventsController {
    private final EventSubscriptionSrv eventSubscriptionSrv;

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void subscribeToFuturEvents(@RequestBody final EventSubscriptionRQ request) {
        this.eventSubscriptionSrv.subscribeToFutureEvents(request.chatId());
    }
}
