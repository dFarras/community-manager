package org.vtes.communitymanager.handlers;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class GoogleHttpTransport {
    private static final String ERROR_MSG = "There was an error while trying to initialize http transport.";
    private NetHttpTransport httpTransport;

    public NetHttpTransport getDefaultInstance() {
        if (this.httpTransport == null) {
            this.initHttpTransport();
        }
        return this.httpTransport;
    }

    private void initHttpTransport() {
        try {
            this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(ERROR_MSG);
        }
    }
}
