package sceaj.adyenmock.api.v1.model.webhook;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WebhookPayload {

    private Boolean live;
    private List<NotificationRequestItem> notificationItems;
}
