package org.vtes.communitymanager.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.vtes.communitymanager.availability.*;
import org.vtes.communitymanager.event.EventDto;
import org.vtes.communitymanager.event.InternalEventSrv;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventChecker {
    private final InternalAvailabilitySrv internalAvailabilitySrv;
    private final InternalEventSrv internalEventSrv;

    @Scheduled(cron = "0 0 11 15 * ?")
    public void checkPlayerAvailability() {
        LocalDate startOfWeek = this.getStartOfWeek();
        AvailabilityAggregator availabilityAggregator = new AvailabilityAggregator();
        List<AvailabilityDto> possibleAvailabilities = this.internalAvailabilitySrv.getAvailabilitiesForWeek(startOfWeek);
        possibleAvailabilities
            .stream()
            .map(AvailabilityDto::getAvailability)
            .flatMap(Collection::stream)
            .forEach(availabilityAggregator::addAvailability);
        Optional<AvailabilitySelection> eventSelection = availabilityAggregator.getMaxAvailability();
        if (eventSelection.isPresent()) {
            final AvailabilitySelection selectedDate = eventSelection.get();
            final List<String> chatIds = possibleAvailabilities
                .stream()
                .filter(availability -> this.isAvailableFor(availability.getAvailability(), selectedDate))
                .map(AvailabilityDto::getChatId)
                .toList();
            final EventDto eventDto = EventDto.builder()
                .title("Partidas abiertas")
                .description("some random description")
                .bannerUrl("somebannerurl")
                .start(this.getStart(startOfWeek, selectedDate))
                .end(this.getEnd(startOfWeek, selectedDate))
                .attendees(chatIds)
                .outstanding(false)
                .build();
            this.internalEventSrv.create(eventDto);
        }
    }

    private boolean isAvailableFor(final List<DailyAvailabilityDto> dailyAvailabilities, final AvailabilitySelection selectedDate) {
        final WeekDayEnum weekDay = selectedDate.getWeekDay();
        final ScheduleEnum schedule = selectedDate.getSchedule();
        return dailyAvailabilities
            .stream()
            .anyMatch(dailyAvailability ->
                weekDay.equals(dailyAvailability.getWeekDay()) && schedule.equals(dailyAvailability.getSchedule())
            );
    }

    private LocalDate getStartOfWeek() {
        DayOfWeek weekStart = DayOfWeek.MONDAY;
        LocalDate now = LocalDate.now();
        return now.with(TemporalAdjusters.previousOrSame(weekStart));
    }

    private OffsetDateTime getStart(final LocalDate startOfWeek, final AvailabilitySelection selectedDate) {
        OffsetTime startTime = selectedDate.getSchedule().getStart().atOffset(ZoneOffset.of("Europe/Berlin"));
        LocalDate startDate = startOfWeek.plusDays(selectedDate.getWeekDay().getIndex());
        return startDate.atTime(startTime);
    }
    private OffsetDateTime getEnd(final LocalDate startOfWeek, final AvailabilitySelection selectedDate) {
        OffsetTime endTime = selectedDate.getSchedule().getEnd().atOffset(ZoneOffset.of("Europe/Berlin"));
        LocalDate startDate = startOfWeek.plusDays(selectedDate.getWeekDay().getIndex());
        return startDate.atTime(endTime);
    }
}
