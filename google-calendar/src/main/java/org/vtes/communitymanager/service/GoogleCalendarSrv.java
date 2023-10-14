package org.vtes.communitymanager.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vtes.communitymanager.handlers.GoogleCalendarHandler;
import org.vtes.communitymanager.mapper.ItemDtoMapper;
import org.vtes.communitymanager.model.EventDto;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleCalendarSrv {
    private static final String GOOGLE_CALENDAR_ID = "2f61769ebb6aa5c52dc747ce6f34de3c694623e3897f59d5ba270fe67ff7a249@group.calendar.google.com";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private final GoogleCalendarHandler googleCalendarHandler;
    private final ItemDtoMapper itemDtoMapper;

    public List<EventDto> findEvents() {
        OffsetDateTime startOffset = OffsetDateTime.now(ZoneId.of("Europe/Madrid"));
        OffsetDateTime endOffset = startOffset.plusMonths(1L);
        final List<Event> items = this.getEvents(startOffset, endOffset);
        return items == null || items.isEmpty() ? new ArrayList<>() : this.itemDtoMapper.from(items);
    }

    public void createEvent(final EventDto eventDto) {
        final Calendar service = this.googleCalendarHandler.getGoogleCalendar();
        final Event googleEvent = new Event();
        googleEvent.setSummary(eventDto.getTitle());
        googleEvent.setLocation("Calle de las Peñuelas, 14, 28005 Madrid, España");
        googleEvent.setDescription(eventDto.getDescription());
        googleEvent.setStart(this.from(eventDto.getStart()));
        googleEvent.setEnd(this.from(eventDto.getEnd()));
        googleEvent.setVisibility("public");
        googleEvent.setAttendees(this.buildAttendees(eventDto.getAttendees()));
        try {
            service.events().insert(GOOGLE_CALENDAR_ID, googleEvent).execute();
        } catch (IOException e) {
            throw new RuntimeException("Error while inserting event on google calendar.");
        }
    }

    private List<EventAttendee> buildAttendees(final List<String> attendeeEmails) {
        return attendeeEmails.stream()
            .map(mail -> {
                final EventAttendee attendee = new EventAttendee();
                attendee.setEmail(mail);
                return attendee;
            }).collect(Collectors.toList());
    }

    private EventDateTime from(final OffsetDateTime value) {
        final EventDateTime result = new EventDateTime();
        result.setDateTime(new DateTime(DATE_FORMATTER.format(value)));
        result.setTimeZone(value.getOffset().getId());
        return result;
    }

    private List<Event> getEvents(
        final OffsetDateTime startDate,
        final OffsetDateTime endDate
    ) {
        final DateTime start = new DateTime(DATE_FORMATTER.format(startDate));
        final DateTime end = new DateTime(DATE_FORMATTER.format(endDate));
        final Calendar service = this.googleCalendarHandler.getGoogleCalendar();
        Events result;
        try {
            result = service.events().list(GOOGLE_CALENDAR_ID)
                .setMaxResults(20)
                .setTimeMin(start)
                .setTimeMax(end)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        } catch (final IOException e) {
            throw new RuntimeException("Error fetching events from Google API.");
        }
        return result.getItems();
    }
}
