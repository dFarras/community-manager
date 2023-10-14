package org.vtes.communitymanager.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventRqMapper {
    public EventDto from(final EventRq eventRq) {
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
