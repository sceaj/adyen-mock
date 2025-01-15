package sceaj.adyenmock.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/webhooks")
public class WebhookController {

    @PostMapping(value = "/event", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> receiveWebhookEvent(@RequestBody String request) {
        log.info("Webhook event received = {}", request);
        return ResponseEntity.accepted().build();
    }
}
