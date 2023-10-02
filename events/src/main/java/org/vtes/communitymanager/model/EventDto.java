package org.vtes.communitymanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class EventDto {
    private final String title;
    private final String description;
    private final OffsetDateTime date;
}
