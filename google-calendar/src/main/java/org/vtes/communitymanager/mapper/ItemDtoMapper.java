package org.vtes.communitymanager.mapper;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import org.springframework.stereotype.Component;
import org.vtes.communitymanager.model.EventDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemDtoMapper {
    public List<EventDto> from(List<Event> events) {
        return events.stream()
            .map(this::from)
            .collect(Collectors.toList());
    }
    public EventDto from(Event event) {
        final String strStartDateTime = event.getStart().getDateTime().toString();
        final String strEndDateTime = event.getEnd().getDateTime().toString();
        final List<String> attendeeEmails = event.getAttendees().stream().map(EventAttendee::getEmail).toList();
        return EventDto.builder()
            .title(event.getSummary())
            .description(event.getDescription())
            .start(OffsetDateTime.parse(strStartDateTime))
            .end(OffsetDateTime.parse(strEndDateTime))
            .attendees(attendeeEmails)
            .build();
    }
}
