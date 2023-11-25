package org.vtes.communitymanager.availability;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
@AllArgsConstructor
public class AvailabilityReadRepository {
    private final MongoTemplate template;

    public List<AvailabilityDto> getAvailabilitiesForWeek(final LocalDate weekStart) {
        final OffsetDateTime eventDate = weekStart.atStartOfDay().atOffset(ZoneOffset.of("Europe/Berlin"));



        Query query = new Query();
        query.addCriteria(Criteria.where("startDate").gte(eventDate.minusDays(1L)));
        query.addCriteria(Criteria.where("startDate").lte(eventDate.plusDays(1L)));
        return template.find(query, AvailabilityDocument.class).stream().map(this::from).toList();
    }

    private AvailabilityDto from(final AvailabilityDocument availabilityDocument) {
        return AvailabilityDto.builder()
            .id(availabilityDocument.getId())
            .chatId(availabilityDocument.getChatId())
            .weekStart(availabilityDocument.getWeekStart())
            .weekEnd(availabilityDocument.getWeekEnd())
            .availability(availabilityDocument.getAvailability())
            .build();
    }
}
