package org.vtes.communitymanager.create;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vtes.communitymanager.event.InternalEventSrv;
import org.vtes.communitymanager.event.EventDto;
import org.vtes.communitymanager.service.GoogleCalendarSrv;

@Service
@RequiredArgsConstructor
public class CreateEventSrv {
    private final InternalEventSrv internalEventSrv;
    private final GoogleCalendarSrv googleCalendarSrv;

    public EventDto createEvent(final EventDto eventDto) {
        eventDto.setId(null);
        eventDto.addAttendee("vtesguarida@gmail.com");
        this.googleCalendarSrv.createEvent(eventDto);
        return this.internalEventSrv.create(eventDto);
    }
}
