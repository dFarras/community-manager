package org.vtes.communitymanager.create;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vtes.communitymanager.event.EventDto;
import org.vtes.communitymanager.model.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class CreateEventController {
    private final CreateEventSrv createEventSrv;
    private final EventRQMapper eventRqMapper;
    private final EventRsMapper eventRsMapper;

    @PreAuthorize("hasRole('ROLE_vtes-player')")
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public EventRS createEvent(
        @RequestBody EventRQ eventRq
    ) {
        final EventDto request = this.eventRqMapper.from(eventRq);
        final EventDto eventDto = this.createEventSrv.createEvent(request);
        return this.eventRsMapper.from(eventDto);
    }
}
