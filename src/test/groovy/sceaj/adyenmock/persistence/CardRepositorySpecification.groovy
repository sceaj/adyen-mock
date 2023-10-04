package sceaj.adyenmock.persistence

import org.springframework.beans.factory.annotation.Autowired

import sceaj.adyenmock.SpringContextSpecification
import sceaj.adyenmock.persistence.entities.CardEntity
import spock.lang.Subject

class CardRepositorySpecification extends SpringContextSpecification {
	
	@Autowired
	@Subject
	CardRepository repository;
	
	def "CardRepository - save"() {
		
		given:
		def entity = new CardEntity()
		entity.externalKey = UUID.fromString('52D0C32D-B421-4E26-84BA-A5D205E50F74')
		entity.cardNumber = '2021091713134708'
		entity.nameOnCard = 'Robert Grott'
		entity.expirationMonth = 5
		entity.expirationYear = 2027
		entity.cvv = '281'
		
		when:
		def persistentEntity = repository.save(entity)
		
		then:
		persistentEntity != null
		persistentEntity.recordId > 0
	}
	
	def "CardRepository - findByExternalKey"() {

		given:
		def externalKey = UUID.fromString('05B48194-3793-4364-9BA0-0BB233E2D9DB')
		
		when:
		def entity = repository.findByExternalKey(externalKey)
		
		then:
		entity.cardNumber == '4042564983652006'
		entity.nameOnCard == 'Howard Lo'
		entity.expirationMonth == 8
		entity.expirationYear == 2025
		entity.cvv == '131'
		
	}
}
