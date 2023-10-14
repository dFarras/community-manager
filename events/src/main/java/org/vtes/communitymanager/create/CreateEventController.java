package org.vtes.communitymanager.create;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vtes.communitymanager.model.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class CreateEventController {
    private final CreateEventSrv createEventSrv;
    private final EventRqMapper eventRqMapper;
    private final EventRsMapper eventRsMapper;

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public EventRS createEvent(
        @RequestBody EventRq eventRq
    ) {
        final EventDto request = this.eventRqMapper.from(eventRq);
        final EventDto eventDto = this.createEventSrv.createEvent(request);
        return this.eventRsMapper.from(eventDto);
    }
}
