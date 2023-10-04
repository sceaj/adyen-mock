package sceaj.adyenmock.cards;

import org.mapstruct.Mapper;

import sceaj.adyenmock.api.v1.model.Address;
import sceaj.adyenmock.api.v1.model.Card;
import sceaj.adyenmock.api.v1.model.CardHolder;
import sceaj.adyenmock.persistence.entities.AddressEmbeddable;
import sceaj.adyenmock.persistence.entities.CardEntity;
import sceaj.adyenmock.persistence.entities.CardHolderEntity;

@Mapper
public interface CardMapper {
	
	CardEntity cardToCardEntity(Card card);
	
	Card cardEntityToCard(CardEntity entity);
	
	CardHolderEntity cardHolderToCardHolderEntity(CardHolder cardHolder);
	
	CardHolder cardHolderEntityToCardHolder(CardHolderEntity entity);
	
	AddressEmbeddable addressToAddressEmbeddable(Address address);
	
	Address addressEmbeddableToAddress(AddressEmbeddable embeddable);

}
