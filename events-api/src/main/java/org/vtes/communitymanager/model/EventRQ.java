package org.vtes.communitymanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class EventRQ {
    private String id;
    private String title;
    private String description;
    private String bannerUrl;
    private OffsetDateTime start;
    private OffsetDateTime end;
    private List<String> attendees;
}
