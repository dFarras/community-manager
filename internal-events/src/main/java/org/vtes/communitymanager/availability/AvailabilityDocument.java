package org.vtes.communitymanager.availability;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "Availability")
public class AvailabilityDocument {
    @Id
    private String id;
    private String chatId;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private List<DailyAvailabilityDto> availability;
}
