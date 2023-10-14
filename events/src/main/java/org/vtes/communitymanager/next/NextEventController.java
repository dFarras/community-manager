package org.vtes.communitymanager.next;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vtes.communitymanager.model.EventRS;
import org.vtes.communitymanager.model.EventDto;
import org.vtes.communitymanager.model.EventRsMapper;


@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class NextEventController {

    private final NextEventSrv nextEventSrv;
    private final EventRsMapper eventRsMapper;

    @GetMapping(
        value = "next",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public EventRS getNextEvent() {
        EventDto event = this.nextEventSrv.findNextEvent();
        return event.isEmpty() ? null : this.eventRsMapper.from(event);
    }
}
