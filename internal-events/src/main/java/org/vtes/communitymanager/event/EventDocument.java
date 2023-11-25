package org.vtes.communitymanager.event;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "Event")
public class EventDocument {
    @Id
    private String id;
    private String title;
    private String description;
    private String bannerUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> attendees;
    private boolean outstanding;
}
