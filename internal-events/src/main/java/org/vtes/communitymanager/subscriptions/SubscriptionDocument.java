package org.vtes.communitymanager.subscriptions;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "Subscription")
public class SubscriptionDocument {
    @Id
    private String id;
    private String chatId;
}
