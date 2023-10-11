package sceaj.adyenmock.api.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import sceaj.adyenmock.utils.AdyenHmacUtils;

@Slf4j
@RestController
public class NotificationControllerV1 {

	private final static String ACCEPT_WEBHOOK_OPERATION_DOC = "Accepts and acknowledges an Adyen Webhook.  This is only used for capturing and validating webhooks that "
			+ "are actually sent by the Adyen test environment.";

	private AdyenHmacUtils hmacHelper;
	
	public NotificationControllerV1(final AdyenHmacUtils hmacUtils) {
		hmacHelper = hmacUtils;
	}

	@Operation(method = "POST", description = ACCEPT_WEBHOOK_OPERATION_DOC)
	@PostMapping(value = "/api/v1/notifications/adyen", consumes = APPLICATION_JSON_VALUE)
	public String acceptWebhook(@RequestBody JsonNode adyenPayload) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(adyenPayload);
		log.info("Adyen Webhook: {}", json);
		if (adyenPayload.get("live").asBoolean()) {
			log.warn(
					"AdyenMock just accepted a LIVE WEBHOOK - Make sure your Live Adyen configurations are correct.  Your production environment may be missing important Adyen webhooks");
		}
		if (adyenPayload.get("notificationItems").isArray()) {
			Iterator<JsonNode> itemIterator = adyenPayload.get("notificationItems").elements();
			while (itemIterator.hasNext()) {
				JsonNode notificationItem = itemIterator.next();
				// Validate HMAC
				String hmacSignature = extractValueAsText(notificationItem, "NotificationRequestItem", "additionalData", "hmacSignature");
				String hmacPayload = buildAdyenHmacPayload(notificationItem);
				if (!hmacHelper.validateHmacSignature(hmacSignature, hmacPayload)) {
					log.warn("HMAC validation failed. Provided Signature: {}, Calculated Signature: {}", hmacSignature, hmacHelper.calculateHmacSignature(hmacPayload));
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Security Failed");
				}
			}
			return "[accepted]";
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expected 'notificationItems' in root object.");
		}
	}
	
	private String buildAdyenHmacPayload(final JsonNode notificationRoot) {
		
		StringBuilder sb = new StringBuilder();
		// pspReference
		sb.append(extractValueAsText(notificationRoot, "NotificationRequestItem", "pspReference"));
		sb.append(":");		
		// originalReference	
		sb.append(":");
		// merchantAccountCode	TestMerchant
		sb.append(extractValueAsText(notificationRoot, "NotificationRequestItem", "merchantAccountCode"));
		sb.append(":");
		// merchantReference	TestPayment-1407325143704
		sb.append(extractValueAsText(notificationRoot, "NotificationRequestItem", "merchantReference"));
		sb.append(":");
		// value	1130
		sb.append(extractValueAsText(notificationRoot, "NotificationRequestItem", "amount", "value"));
		sb.append(":");
		// currency	EUR
		sb.append(extractValueAsText(notificationRoot, "NotificationRequestItem", "amount", "currency"));
		sb.append(":");
		// eventCode	AUTHORISATION
		sb.append(extractValueAsText(notificationRoot, "NotificationRequestItem", "eventCode"));
		sb.append(":");
		// success	true
		sb.append(extractValueAsText(notificationRoot, "NotificationRequestItem", "success"));
	
		return sb.toString();
	}
	
	private String extractValueAsText(final JsonNode notificationRoot, final String... fieldNames) {
		int pathLength = fieldNames.length;
		JsonNode node = notificationRoot;
		int level = 1;
		while (level <= pathLength) {
			node = node.path(fieldNames[level++ - 1]);
		}
		return node.asText();
	}

}
