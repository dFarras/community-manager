package org.vtes.communitymanager.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventRsMapper {
    public EventRS from(EventDto eventDto) {
        EventRS.EventRSBuilder builder =  EventRS.builder()
            .id(eventDto.getId())
            .title(eventDto.getTitle())
            .description(eventDto.getDescription())
            .bannerUrl(eventDto.getBannerUrl())
            .start(eventDto.getStart())
            .end(eventDto.getEnd());

        List<String> attendees = eventDto.getAttendees();
        if (attendees != null && !attendees.isEmpty()) {
            builder.attendees(eventDto.getAttendees().size());
        }

        return builder.build();
    }
}
