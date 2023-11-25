package org.vtes.communitymanager.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternalAvailabilitySrv {
    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityReadRepository availabilityReadRepository;

    public List<AvailabilityDto> getAvailabilitiesForWeek(final LocalDate weekStart) {
        return this.availabilityReadRepository.getAvailabilitiesForWeek(weekStart);
    }

    public void declareAvailability(final AvailabilityDto availabilityDto) {
        final String id = UUID.randomUUID().toString();
        final AvailabilityDocument availability = new AvailabilityDocument(
            id,
            availabilityDto.getChatId(),
            availabilityDto.getWeekStart(),
            availabilityDto.getWeekEnd(),
            availabilityDto.getAvailability()
        );
        this.availabilityRepository.save(availability);
    }
}
