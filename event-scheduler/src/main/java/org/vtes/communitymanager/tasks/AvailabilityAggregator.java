package org.vtes.communitymanager.tasks;

import org.vtes.communitymanager.availability.DailyAvailabilityDto;

import java.util.*;

public class AvailabilityAggregator {
    private final Map<AvailabilitySelection, Integer> aggregator = new HashMap<>();

    public void addAvailability(final DailyAvailabilityDto dailyAvailabilityDto) {
        final AvailabilitySelection id = new AvailabilitySelection(dailyAvailabilityDto.getWeekDay(), dailyAvailabilityDto.getSchedule());
        final int value = this.aggregator.get(id) != null ? this.aggregator.get(id): 0;
        this.aggregator.put(id, value + 1);
    }

    public Optional<AvailabilitySelection> getMaxAvailability() {
        Optional<Integer> optAttendees = this.aggregator.values().stream().filter(a -> a > 4).max(Comparator.naturalOrder());
        if(optAttendees.isPresent()) {
            final Integer attendees = optAttendees.get();
            List<AvailabilitySelection> targets = this.aggregator
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(attendees))
                .map(Map.Entry::getKey)
                .toList();
            final Random random = new Random();
            return Optional.of(targets.get(random.nextInt(targets.size())));
        }
        return Optional.empty();
    }
}
