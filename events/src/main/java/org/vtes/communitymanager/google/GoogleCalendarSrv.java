package org.vtes.communitymanager.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vtes.communitymanager.model.EventDto;
import org.vtes.communitymanager.next.NextEventSrv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GoogleCalendarSrv {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private final ItemDtoMapper itemDtoMapper;

    public List<EventDto> findEvents() {
        List<Event> items;
        final NetHttpTransport HTTP_TRANSPORT;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Calendar service =
                new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            DateTime now = new DateTime(System.currentTimeMillis());
            Events events = service.events().list("2f61769ebb6aa5c52dc747ce6f34de3c694623e3897f59d5ba270fe67ff7a249@group.calendar.google.com")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
            items = events.getItems();
            if (items.isEmpty()) {
                System.out.println("No upcoming events found.");
            } else {
                System.out.println("Upcoming events");
                for (Event event : items) {
                    DateTime start = event.getStart().getDateTime();
                    if (start == null) {
                        start = event.getStart().getDate();
                    }
                    System.out.printf("%s (%s)\n", event.getSummary(), start);
                }
            }
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<EventDto> nextEvents = itemDtoMapper.from(items);

        return nextEvents;
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
        throws IOException {
        // Load client secrets.
        InputStream in = NextEventSrv.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }
}