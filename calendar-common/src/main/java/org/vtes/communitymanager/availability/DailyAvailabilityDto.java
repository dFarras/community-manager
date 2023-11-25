package org.vtes.communitymanager.availability;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vtes.communitymanager.availability.ScheduleEnum;
import org.vtes.communitymanager.availability.WeekDayEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyAvailabilityDto {
    private WeekDayEnum weekDay;
    private ScheduleEnum schedule;
}
