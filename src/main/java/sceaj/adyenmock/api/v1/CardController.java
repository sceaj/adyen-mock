package sceaj.adyenmock.api.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import sceaj.adyenmock.api.v1.model.Card;
import sceaj.adyenmock.cards.CardService;

@RestController
public class CardController {
	
	private final static String CARD_CREATE_OPERATION_DOC = "Create a new card to used with the mock APIs.  Similar validations to an actual payment provider are performed " +
			"such as Luhn checks, expiration checks etc.  You can avoid the complexity of determining card numbers that will pass the " +
			"Luhn check by adding a request parameter that specifies generateCheckDigit=true, in which case the registered card number will " + 
			"have 1 additional digit from the card number supplied and be guaranteed to pass Luhn checks.";
	
	private final CardService service;
	
	public CardController(final CardService cardService) {
		service = cardService;
	}

	
	@Operation(method = "POST", description = CARD_CREATE_OPERATION_DOC)
	@PostMapping(value = "/api/v1/cards", consumes = APPLICATION_JSON_VALUE)
	public Card createCard(@RequestBody Card card, @RequestParam("generateCheckDigit") Boolean generateCheckDigit) {
		return service.createCard(card, generateCheckDigit);
	}
}
