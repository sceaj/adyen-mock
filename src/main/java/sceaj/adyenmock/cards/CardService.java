package sceaj.adyenmock.cards;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sceaj.adyenmock.api.v1.model.Card;
import sceaj.adyenmock.persistence.CardRepository;
import sceaj.adyenmock.utils.CardNumberUtils;

@Service
public class CardService {

	private final CardRepository repository;
	
	@Autowired
	public CardService(final CardRepository cardRepository) {
		repository = cardRepository;
	}
	
	public Card createCard(final Card cardData, final boolean generateCheckDigit) {

		String cardNumber = cardData.getCardNumber();
		if (generateCheckDigit) {
			StringBuilder sb = new StringBuilder(cardNumber);
			sb.append(CardNumberUtils.checkDigit(cardNumber));
			cardNumber = sb.toString();
		}
		Card stubCard = new Card(UUID.randomUUID(), cardNumber, cardData.getNameOnCard(), cardData.getExpirationMonth(), cardData.getExpirationYear(), cardData.getCvv(), cardData.getCardHolder());
		return stubCard;
	}
}
