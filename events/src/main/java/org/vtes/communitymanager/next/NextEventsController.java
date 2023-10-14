package org.vtes.communitymanager.next;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vtes.communitymanager.model.EventDto;
import org.vtes.communitymanager.model.EventRS;
import org.vtes.communitymanager.model.EventRsMapper;
import org.vtes.communitymanager.service.GoogleCalendarSrv;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("events")
public class NextEventsController {
    private final NextEventSrv nextEventSrv;
    private final EventRsMapper eventRsMapper;

    @GetMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<EventRS> getNextEvents() {
        List<EventDto> results = this.nextEventSrv.findNextEvents();
        return results.stream().map(this.eventRsMapper::from).collect(Collectors.toList());
    }
}
