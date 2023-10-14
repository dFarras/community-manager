package org.vtes.communitymanager.next;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vtes.communitymanager.model.EventDto;
import org.vtes.communitymanager.model.EventRS;
import org.vtes.communitymanager.model.EventRsMapper;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("events")
public class NextOutstandingEventController {
    private final NextEventSrv nextEventSrv;
    private final EventRsMapper eventRsMapper;

    @GetMapping(
        value = "outstanding",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public EventRS getNextOutstandingEvent() {
        final EventDto event = this.nextEventSrv.findNextOutstandingEvent();
        return event.isEmpty() ? null : this.eventRsMapper.from(event);
    }

}
