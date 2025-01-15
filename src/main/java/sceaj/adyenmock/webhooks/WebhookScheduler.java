package sceaj.adyenmock.webhooks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import sceaj.adyenmock.persistence.WebhookRepository;
import sceaj.adyenmock.persistence.entities.WebhookEntity;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class WebhookScheduler {

    private final WebhookRepository webhookRepository;
    private final RestTemplate restTemplate;

    @Value("${webhook.retries.max-attempts:3}")
    private int retries;

    @Value("${webhook.retries.interval:30}")
    private int baseInterval;


    public WebhookScheduler(WebhookRepository webhookRepository) {
        this.webhookRepository = webhookRepository;
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(fixedRate = 30000)
    public void sendWebhookEvent() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMinutes(1);
        List<WebhookEntity> pendingWebhooks = webhookRepository.findAllByStatusAndDeliveryDateBetween(WebhookStatus.PENDING, startTime, endTime);

        log.info("WebhookScheduler - found {} pending events", pendingWebhooks.size());

        pendingWebhooks.forEach(this::sendWebhook);
    }

    private void sendWebhook(WebhookEntity webhook) {
        try {
            HttpEntity<String> entity = new HttpEntity<>(webhook.getPayload(), getHeaders());
            restTemplate.postForEntity(webhook.getReturnUrl(), entity, String.class);
            handleSuccessResponse(webhook);
        } catch (RestClientResponseException ex) {
            handleFailureResponse(webhook);
        }
    }

    private void handleSuccessResponse(WebhookEntity webhook) {
        webhook.setStatus(WebhookStatus.DELIVERED);
        webhookRepository.save(webhook);
    }

    private void handleFailureResponse(WebhookEntity webhook) {
        if (webhook.getRetries() >= retries) {
            webhook.setStatus(WebhookStatus.FAILED);
        } else {
            webhook.incrementRetries();
            webhook.setDeliveryDate(webhook.getDeliveryDate().plusSeconds(calculateDelay(webhook.getRetries())));
        }
        webhookRepository.save(webhook);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private long calculateDelay(int retry) {
        long delay = (long) Math.pow(2, retry) * baseInterval;
        log.info("Delay in seconds = {}", delay);
        return delay;
    }

}
