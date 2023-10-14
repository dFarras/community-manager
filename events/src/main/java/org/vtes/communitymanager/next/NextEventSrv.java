package org.vtes.communitymanager.next;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vtes.communitymanager.event.EventSrv;
import org.vtes.communitymanager.model.EventDto;
import org.vtes.communitymanager.service.GoogleCalendarSrv;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NextEventSrv {
    private final GoogleCalendarSrv googleCalendarSrv;
    private final EventSrv eventSrv;

    public EventDto findNextEvent() {
        return this.eventSrv.getNextEvent();
    }

    public EventDto findNextOutstandingEvent() {
        return this.eventSrv.getNextOutstandingEvent();
    }

    public List<EventDto> findNextEvents() {
        return this.eventSrv.getNextEvents();
    }

}
