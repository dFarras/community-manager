package org.vtes.communitymanager.next;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vtes.communitymanager.model.EventDto;
import org.vtes.communitymanager.model.EventRS;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class NextEventsController {

    private final NextEventSrv nextEventSrv;

    @GetMapping(
        value = "next",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<EventRS> getNextEvent() {
        List<EventDto> events = this.nextEventSrv.findNextEvent();
        return events.stream()
            .map(this::from)
            .collect(Collectors.toList());
    }

    private EventRS from(EventDto eventDto) {
        return EventRS.builder()
            .title(eventDto.getTitle())
            .description(eventDto.getDescription())
            .date(eventDto.getDate())
            .build();
    }
}
