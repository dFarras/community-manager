package org.vtes.communitymanager.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EventDto {
    public static final EventDto EMPTY = new EventDto();
    private String id;
    private String title;
    private String description;
    private String bannerUrl;
    private OffsetDateTime start;
    private OffsetDateTime end;
    @Builder.Default
    private List<String> attendees = new ArrayList<>();
    private boolean outstanding;

    public void addAttendee(final String email) {
        if (this.attendees == null) {
            this.attendees = new ArrayList<>();
        }
        this.attendees.add(email);
    }

    public boolean isEmpty() {
        return this.equals(EMPTY);
    }
}
