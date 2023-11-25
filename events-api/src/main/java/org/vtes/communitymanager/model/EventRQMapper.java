package org.vtes.communitymanager.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vtes.communitymanager.event.EventDto;

@Component
@RequiredArgsConstructor
public class EventRQMapper {
    public EventDto from(final EventRQ eventRq) {
        return EventDto.builder()
            .id(eventRq.getId())
            .title(eventRq.getTitle())
            .description(eventRq.getDescription())
            .bannerUrl(eventRq.getBannerUrl())
            .start(eventRq.getStart())
            .end(eventRq.getEnd())
            .attendees(eventRq.getAttendees())
            .build();
    }
}
