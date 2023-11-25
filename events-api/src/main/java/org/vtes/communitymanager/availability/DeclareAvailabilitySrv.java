package org.vtes.communitymanager.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeclareAvailabilitySrv {
    private final InternalAvailabilitySrv internalAvailabilitySrv;
    public void declareAvailability(final AvailabilityDto playerAvailability) {
        this.internalAvailabilitySrv.declareAvailability(playerAvailability);
    }
}
