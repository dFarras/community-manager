package org.vtes.communitymanager.next;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vtes.communitymanager.google.GoogleCalendarSrv;
import org.vtes.communitymanager.model.EventDto;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NextEventSrv {
    private final GoogleCalendarSrv googleCalendarSrv;

    public List<EventDto> findNextEvent() {
        return this.googleCalendarSrv.findEvents();
    }

}
