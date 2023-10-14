package org.vtes.communitymanager.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vtes.communitymanager.model.EventDto;
import org.vtes.communitymanager.mongo.EventDocument;
import org.vtes.communitymanager.mongo.EventReadRepository;
import org.vtes.communitymanager.mongo.EventRepository;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventSrv {
    private final EventRepository eventRepository;
    private final EventReadRepository eventReadRepository;

    public EventDto create(final EventDto event) {
        EventDocument document = this.from(event);
        EventDocument result = this.eventRepository.save(document);
        return this.from(result);
    }

    @Transactional(readOnly = true)
    public EventDto getNextEvent() {
        return this.eventReadRepository.findNextEvent()
            .orElse(EventDto.EMPTY);
    }

    @Transactional(readOnly = true)
    public EventDto getNextOutstandingEvent() {
        return this.eventReadRepository.findNextOutstandingEvent()
            .orElse(EventDto.EMPTY);
    }

    @Transactional(readOnly = true)
    public List<EventDto> getNextEvents() {
        return this.eventReadRepository.findNextEvents();
    }

    private EventDto from(final EventDocument eventDocument) {
        return EventDto.builder()
            .id(eventDocument.getId())
            .title(eventDocument.getTitle())
            .description(eventDocument.getDescription())
            .bannerUrl(eventDocument.getBannerUrl())
            .start(eventDocument.getStartDate().atOffset(ZoneOffset.UTC))
            .end(eventDocument.getEndDate().atOffset(ZoneOffset.UTC))
            .attendees(eventDocument.getAttendees())
            .outstanding(eventDocument.isOutstanding())
            .build();
    }

    private EventDocument from(final EventDto eventDto) {
        final String id = UUID.randomUUID().toString();
        return EventDocument.builder()
            .id(id)
            .title(eventDto.getTitle())
            .description(eventDto.getDescription())
            .bannerUrl(eventDto.getBannerUrl())
            .startDate(eventDto.getStart().toLocalDateTime())
            .endDate(eventDto.getEnd().toLocalDateTime())
            .attendees(eventDto.getAttendees())
            .outstanding(eventDto.isOutstanding())
            .build();
    }
}
