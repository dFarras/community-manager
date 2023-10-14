package org.vtes.communitymanager.handlers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GoogleAuthorization {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
    private final GoogleSecretsHandler googleSecretsHandler;
    private final GoogleHttpTransport googleHttpTransport;

    public Credential getCredentials() {
        GoogleClientSecrets clientSecrets = this.googleSecretsHandler.getClientSecrets();
        GoogleAuthorizationCodeFlow flow = this.getAuthorizationFLow(clientSecrets);
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        try {
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        } catch (IOException e) {
            throw new RuntimeException("Error while authorizing user.");
        }
    }

    private GoogleAuthorizationCodeFlow getAuthorizationFLow(final GoogleClientSecrets googleClientSecrets) {
        NetHttpTransport httpTransport = this.googleHttpTransport.getDefaultInstance();
        try {
            final FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH));
            return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, googleClientSecrets, SCOPES)
                .setDataStoreFactory(fileDataStoreFactory)
                .setAccessType("offline")
                .build();
        } catch (final IOException e) {
            throw new RuntimeException("Error initializing authorization code flow.");
        }
    }
}
