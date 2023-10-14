package org.vtes.communitymanager.handlers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleCalendarHandler {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final GoogleAuthorization googleAuthorization;
    private final GoogleHttpTransport googleHttpTransport;

    public Calendar getGoogleCalendar() {
        final NetHttpTransport httpTransport = this.googleHttpTransport.getDefaultInstance();
        Credential credential = this.googleAuthorization.getCredentials();
        return new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }
}
