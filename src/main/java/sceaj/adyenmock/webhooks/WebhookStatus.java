package sceaj.adyenmock.webhooks;

import lombok.Getter;

@Getter
public enum WebhookStatus {

    DELIVERED,
    FAILED,
    PENDING;
}
