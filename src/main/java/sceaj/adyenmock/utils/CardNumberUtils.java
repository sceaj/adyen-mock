package sceaj.adyenmock.utils;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CardNumberUtils {
	
	private static Logger log = LoggerFactory.getLogger(CardNumberUtils.class);
	
	private CardNumberUtils() {
		// No instances
	}
	
	public static int checkDigit(int[] payload) {

		log.debug("checkDigit: {}", payload);
		int checkSum = 0;
		for (int i = 0; i < payload.length; i++) {
			int digit = payload[payload.length - i - 1];
			
			int digitSum = ((i % 2) == 0) ? 2 * digit : digit;
			if (digitSum > 9) digitSum++;
			log.debug("digitSum: {}", digitSum % 10);
			checkSum += digitSum % 10;
		}
		return (10 - (checkSum % 10)) % 10;
	}
	
	public static int checkDigit(final String payload) {
		return checkDigit(convertToIntArray(payload));
	}
	
	public static boolean luhnCheck(int[] cardNumber) {
		int[] payload = Arrays.copyOf(cardNumber, cardNumber.length - 1);
		return checkDigit(payload) == cardNumber[cardNumber.length - 1];
	}
	
	public static boolean luhnCheck(String cardNumber) {
		return luhnCheck(convertToIntArray(cardNumber));
	}
	
	public static int[] convertToIntArray(String input) {
		int[] digits = new int[input.length()];
		for (int i = 0; i < input.length(); i++) {
			digits[i] = input.charAt(i) - '0';
		}
		return digits;
	}
}