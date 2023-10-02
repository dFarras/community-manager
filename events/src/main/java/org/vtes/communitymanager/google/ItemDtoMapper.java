package org.vtes.communitymanager.google;

import com.google.api.services.calendar.model.Event;
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
        String strDateTime = event.getStart().getDateTime().toString();
        return EventDto.builder()
            .title(event.getSummary())
            .description(event.getDescription())
            .date(OffsetDateTime.parse(strDateTime))
            .build();
    }
}
