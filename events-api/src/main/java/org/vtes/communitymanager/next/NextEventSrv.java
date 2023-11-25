package org.vtes.communitymanager.next;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vtes.communitymanager.event.InternalEventSrv;
import org.vtes.communitymanager.event.EventDto;
import org.vtes.communitymanager.service.GoogleCalendarSrv;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NextEventSrv {
    private final GoogleCalendarSrv googleCalendarSrv;
    private final InternalEventSrv internalEventSrv;

    public EventDto findNextEvent() {
        return this.internalEventSrv.getNextEvent();
    }

    public EventDto findNextOutstandingEvent() {
        return this.internalEventSrv.getNextOutstandingEvent();
    }

    public List<EventDto> findNextEvents() {
        return this.internalEventSrv.getNextEvents();
    }

}
