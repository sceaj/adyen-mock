package sceaj.adyenmock.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AdyenHmacUtils {
	
	private final HmacUtils hmacUtility;
	
	public AdyenHmacUtils(@Value("${adyen-mock.webhooks.hmac-key}") final String hmacKey) throws DecoderException {
		log.info("Initializing HmacUtils: {}", hmacKey);
		hmacUtility = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, Hex.decodeHex(hmacKey));
	}
	
	
	public String calculateHmacSignature(String hmacPayload) {
		
		try {
			byte[] hmac = hmacUtility.hmac(hmacPayload);
			Base64 base64 = new Base64();
			return base64.encodeAsString(hmac);
		} catch (IllegalArgumentException e) {
			log.error("HMAC calculation failed: {}", e.getMessage(), e);
			return "";
		}
	}
	
	public boolean validateHmacSignature(final String hmacSignature, final String hmacPayload) {
		String calculatedSignature = calculateHmacSignature(hmacPayload);
		return hmacSignature.equals(calculatedSignature);
	}
	
	
	
//	// YOUR_HMAC_KEY from the Customer Area
//	String hmacKey = "YOUR_HMAC_KEY";
//	// Notification Request JSON
//	String notificationRequestJson = "NOTIFICATION_REQUEST_JSON";
//	HMACValidator hmacValidator = new HMACValidator();
//	NotificationHandler notificationHandler = new NotificationHandler();
//	NotificationRequest notificationRequest = notificationHandler.handleNotificationJson(notificationRequestJson);
//	// Handle multiple notificationRequests
//	List<NotificationRequestItem> notificationRequestItems = notificationRequest.getNotificationItems();
//	for ( NotificationRequestItem notificationRequestItem : notificationRequestItems ) {
//	    // Handle the notification
//	    if ( hmacValidator.validateHMAC(notificationRequestItem, hmacKey) ) {
//	        // Process the notification based on the eventCode
//	        String eventCode = notificationRequestItem.getEventCode();
//	    } else {
//	        // Non valid NotificationRequest
//	        System.out.print("Non valid NotificationRequest");
//	    }
//	}
//	
//	Key	Value
//	pspReference	7914073381342284
//	originalReference	
//	merchantAccountCode	TestMerchant
//	merchantReference	TestPayment-1407325143704
//	value	1130
//	currency	EUR
//	eventCode	AUTHORISATION
//	success	true
//

}
