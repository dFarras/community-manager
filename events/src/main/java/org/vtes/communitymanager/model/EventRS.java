package org.vtes.communitymanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRS {
    private String id;
    private String title;
    private String description;
    private String bannerUrl;
    private OffsetDateTime start;
    private OffsetDateTime end;
    private Integer attendees;
}
