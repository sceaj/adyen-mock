package sceaj.adyenmock.api.v1.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Card {

	private final UUID externalKey;
	private final String cardNumber;
	private final String nameOnCard;
	private final Integer expirationMonth;
	private final Integer expirationYear;
	private final String cvv;
	private final CardHolder cardHolder;

}
