package org.vtes.communitymanager.event;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EventReadRepository {
    private final MongoTemplate template;

    public Optional<EventDto> findNextEvent() {
        Query query = new Query();
        query.addCriteria(Criteria.where("startDate").gte(LocalDateTime.now()));
        query.with(Sort.by(Sort.Direction.DESC, "startDate"));
        List<EventDocument> events = template.find(query, EventDocument.class);
        return events.isEmpty() ? Optional.empty() : Optional.of(this.from(events.get(0)));
    }

    public Optional<EventDto> findNextOutstandingEvent() {
        Query query = new Query();
        query.addCriteria(Criteria.where("outstanding").is(Boolean.TRUE));
        query.addCriteria(Criteria.where("startDate").gte(LocalDateTime.now()));
        query.with(Sort.by(Sort.Direction.DESC, "startDate"));
        List<EventDocument> events = template.find(query, EventDocument.class);
        return events.isEmpty() ? Optional.empty() : Optional.of(this.from(events.get(0)));
    }

    public List<EventDto> findNextEvents() {
        LocalDateTime now = LocalDateTime.now();
        Query query = new Query();
        query.addCriteria(Criteria.where("startDate").gte(now));
        query.addCriteria(Criteria.where("endDate").lt(now.plusMonths(1L)));
        query.with(Sort.by(Sort.Direction.DESC, "startDate"));
        List<EventDocument> events = template.find(query, EventDocument.class);
        return events.stream()
            .map(this::from)
            .collect(Collectors.toList());
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
}
