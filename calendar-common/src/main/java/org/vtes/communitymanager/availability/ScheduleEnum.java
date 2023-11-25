package org.vtes.communitymanager.availability;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public enum ScheduleEnum {
    MORNING(LocalTime.parse("10:30"), LocalTime.parse("14:00")),
    AFTERNOON(LocalTime.parse("17:00"), LocalTime.parse("20:30"));

    private final LocalTime start;
    private final LocalTime end;
}
