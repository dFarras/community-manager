package org.vtes.communitymanager.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeclareAvailabilityController {
    private final DeclareAvailabilitySrv declareAvailabilitySrv;
    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void declareAvailability(@RequestBody final AvailabilityRQ availabilityRQ) {
        AvailabilityDto playerAvailability = new AvailabilityDto(
            availabilityRQ.getChatId(),
            availabilityRQ.getWeekStart(),
            availabilityRQ.getWeekEnd(),
            availabilityRQ.getAvailability()
        );
        this.declareAvailabilitySrv.declareAvailability(playerAvailability);

    }
}
