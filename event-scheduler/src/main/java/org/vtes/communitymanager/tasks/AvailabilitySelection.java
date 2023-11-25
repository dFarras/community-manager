package org.vtes.communitymanager.tasks;

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
public class AvailabilitySelection {
    private WeekDayEnum weekDay;
    private ScheduleEnum schedule;
}
