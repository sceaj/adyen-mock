package sceaj.adyenmock.webhooks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sceaj.adyenmock.api.v1.model.payment.PaymentRequest;
import sceaj.adyenmock.api.v1.model.payment.RequestPaymentMethod;
import sceaj.adyenmock.api.v1.model.webhook.NotificationRequestItem;
import sceaj.adyenmock.api.v1.model.webhook.WebhookPayload;
import sceaj.adyenmock.persistence.WebhookRepository;
import sceaj.adyenmock.persistence.entities.WebhookEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class WebhookService {

    private final WebhookRepository webhookRepository;
    private final ObjectMapper objectMapper;

    @Value("${webhook.base-interval}")
    private int baseInterval;

    public WebhookService(WebhookRepository webhookRepository) {
        this.webhookRepository = webhookRepository;
        this.objectMapper = new ObjectMapper();
    }

    public void createWebhook(PaymentRequest payment) throws JsonProcessingException {
        NotificationRequestItem notificationRequestItem = buildNotificationRequestItem(payment);
        WebhookPayload payload = prepareWebhookPayload(notificationRequestItem);
        WebhookEntity webhookEntity = buildWebhook(payload, payment.getReturnUrl());
        webhookRepository.save(webhookEntity);
    }

    private WebhookEntity buildWebhook(WebhookPayload payload, String returnUrl) throws JsonProcessingException {
        WebhookEntity webhookEntity = new WebhookEntity();
        webhookEntity.setPayload(objectMapper.writeValueAsString(payload));
        webhookEntity.setDeliveryDate(LocalDateTime.now().plusSeconds(baseInterval));
        webhookEntity.setStatus(WebhookStatus.PENDING);
        webhookEntity.setRetries(0);
        webhookEntity.setReturnUrl(returnUrl);
        return webhookEntity;
    }

    private static WebhookPayload prepareWebhookPayload(NotificationRequestItem notificationRequestItem) {
        return WebhookPayload.builder()
                .live(Boolean.FALSE)
                .notificationItems(List.of(notificationRequestItem))
                .build();
    }

    private NotificationRequestItem buildNotificationRequestItem(PaymentRequest request) {
        String authorizationCode = UUID.randomUUID().toString();
        return NotificationRequestItem.builder()
                .amount(request.getAmount())
                .eventCode(EventCode.AUTHORISATION.name())
                .eventDate(OffsetDateTime.now().toString())
                .merchantAccountCode(request.getMerchantAccount())
                .merchantReference(request.getMerchantAccount())
                .paymentMethod("visa")
                .pspReference(request.getReference())
                .success(Boolean.TRUE.toString())
                .reason(buildNotificationItemReason(authorizationCode, request.getPaymentMethod()))
                .build();
    }

    private String buildNotificationItemReason(String authorizationCode, RequestPaymentMethod paymentMethod) {
        String cardNumberInfo = paymentMethod.getEncryptedCardNumber().substring(11, 15);
        return authorizationCode + cardNumberInfo + paymentMethod.getEncryptedExpiryMonth() + paymentMethod.getEncryptedExpiryYear();
    }
}
