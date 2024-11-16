package sceaj.adyenmock.api.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.http.MediaType.TEXT_XML_VALUE;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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
	@PostMapping(value = "/api/v1/notifications/adyen", consumes = TEXT_XML_VALUE, produces = TEXT_PLAIN_VALUE)
	public String acceptWebhook(@RequestBody String adyenPayload) {
		log.info("Adyen SOAP Webhook: {}", adyenPayload);
		XmlMapper mapper = new XmlMapper();
		try {
			Map<String,Object> xmlPayload = mapper.readValue(adyenPayload, new TypeReference<Map<String,Object>>(){});
			Map<String,Object> notificationRequestItem = extractNotificationRequestItemFromXml(xmlPayload);
			String hmacSignature = extractHmacSignatureFromXml(notificationRequestItem);
			log.debug("HMAC Signature: {}", hmacSignature);
			String hmacPayload = extractHmacPayloadFromXml(notificationRequestItem);
			log.debug("Calculated Signature: {}", hmacHelper.calculateHmacSignature(hmacPayload));
		} catch (JsonProcessingException e) {
			log.error("Exception:", e);
		}
		return "[accepted]";
	}

	@Operation(method = "POST", description = ACCEPT_WEBHOOK_OPERATION_DOC)
	@PostMapping(value = "/api/v1/notifications/adyen", consumes = APPLICATION_JSON_VALUE, produces = TEXT_PLAIN_VALUE)
	public String acceptWebhook(@RequestBody JsonNode adyenPayload) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(adyenPayload);
		log.info("Adyen Webhook: {}", json);
		if (adyenPayload.get("live").asBoolean()) {
			log.warn("AdyenMock just accepted a LIVE WEBHOOK - Make sure your Live Adyen configurations are correct.  Your production environment may be missing important Adyen webhooks");
		}
		if (adyenPayload.get("notificationItems").isArray()) {
			Iterator<JsonNode> itemIterator = adyenPayload.get("notificationItems").elements();
			while (itemIterator.hasNext()) {
				JsonNode notificationItem = itemIterator.next();
				// Validate HMAC
				String hmacSignature = extractValueAsText(notificationItem, "NotificationRequestItem", "additionalData", "hmacSignature");
				String hmacPayload = extractHmacPayloadFromJson(notificationItem);
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
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> extractNotificationRequestItemFromXml(final Map<String,Object> notificationRoot) {
		Map<String, Object> body = (Map<String, Object>) notificationRoot.get("Body");
		Map<String, Object> sendNotification = (Map<String, Object>) body.get("sendNotification");
		Map<String, Object> notification = (Map<String, Object>) sendNotification.get("notification");
		Map<String, Object> notificationItems = (Map<String, Object>) notification.get("notificationItems");
		return (Map<String, Object>) notificationItems.get("NotificationRequestItem");
		
	}
	
	@SuppressWarnings("unchecked")
	private String extractHmacSignatureFromXml(final Map<String,Object> notificationRequestItem) {
		Map<String, Object> additionalData = (Map<String, Object>) notificationRequestItem.get("additionalData");
		List<Map<String, Object>> entry = (List<Map<String, Object>>) additionalData.get("entry");
		return findEntryValue(entry, "hmacSignature");
	}
	
	@SuppressWarnings("unchecked")
	private String findEntryValue(List<Map<String,Object>> entries, String key) {
		return (String) entries.stream()
				.filter(e -> key.equals(((Map<String,Object>)e.get("key")).get("")))
				.findFirst()
				.map(e -> ((Map<String,Object>)e.get("value")).get(""))
				.orElse(null);
		
	}
	
	@SuppressWarnings("unchecked")
	private String extractHmacPayloadFromXml(final Map<String,Object> notificationRequestItem) {

		Map<String, Object> amount = (Map<String, Object>) notificationRequestItem.get("amount");
	
		
		String pspReference = (String) notificationRequestItem.get("pspReference");
		String originalReference = (String) notificationRequestItem.get("originalReference");
		String merchantAccountCode = (String) notificationRequestItem.get("merchantAccountCode");
		String merchantReference = (String) notificationRequestItem.get("merchantReference");
		int amountValue = Integer.valueOf((String) amount.get("value"));
		String amountCurrency = (String) amount.get("currency");
		String eventCode = (String) notificationRequestItem.get("eventCode");
		String success = (String) notificationRequestItem.get("success");
	
		return buildAdyenHmacPayload(pspReference, originalReference, merchantAccountCode, merchantReference, amountValue, amountCurrency, eventCode, success);
	}

	private String extractHmacPayloadFromJson(final JsonNode notificationRoot) {
		
		String pspReference = extractValueAsText(notificationRoot, "NotificationRequestItem", "pspReference");
		String originalReference = extractValueAsText(notificationRoot, "NotificationRequestItem", "originalReference");
		String merchantAccountCode = extractValueAsText(notificationRoot, "NotificationRequestItem", "merchantAccountCode");
		String merchantReference = extractValueAsText(notificationRoot, "NotificationRequestItem", "merchantReference");
		int amountValue = Integer.valueOf(extractValueAsText(notificationRoot, "NotificationRequestItem", "amount", "value"));
		String amountCurrency = extractValueAsText(notificationRoot, "NotificationRequestItem", "amount", "currency");
		String eventCode = extractValueAsText(notificationRoot, "NotificationRequestItem", "eventCode");
		String success = extractValueAsText(notificationRoot, "NotificationRequestItem", "success");
	
		return buildAdyenHmacPayload(pspReference, originalReference, merchantAccountCode, merchantReference, amountValue, amountCurrency, eventCode, success);
	}
	
	private String buildAdyenHmacPayload(final String pspReference,
			final String originalReference,
			final String merchantAccountCode,
			final String merchantReference,
			final int amountValue,
			final String amountCurrency,
			final String eventCode,
			final String success) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(pspReference);
		sb.append(":");
		if (!StringUtils.isAllBlank(originalReference)) {
			sb.append(originalReference);
		}
		sb.append(":");
		sb.append(merchantAccountCode);
		sb.append(":");
		sb.append(merchantReference);
		sb.append(":");
		sb.append(amountValue);
		sb.append(":");
		sb.append(amountCurrency);
		sb.append(":");
		sb.append(eventCode);
		sb.append(":");
		sb.append(success);
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
