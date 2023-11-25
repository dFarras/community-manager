package org.vtes.communitymanager.availability;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDto {
    private String id;
    private String chatId;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private List<DailyAvailabilityDto> availability;
}
