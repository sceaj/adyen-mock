package sceaj.adyenmock.utils

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class AdyenHmacUtilsSpecification extends Specification {
	
//	static hmacKey = '44782DEF547AAA06C910C43932B1EB0C71FC68D9D0C057550C48EC2ACF6BA056'
	static hmacKey = '53D73140BB57D4799ED9DB5E83AA658093D46A5BDBAE9182BAC9DE573B04C40C'
	
	@Subject
	def adyenHmacUtils = new AdyenHmacUtils(hmacKey)
	
	def "validateHmacSignature Tests"() {
		
		given:
		def hmacPayload = '7914073381342284::TestMerchant:TestPayment-1407325143704:1130:EUR:AUTHORISATION:true'
	
		expect:
		adyenHmacUtils.validateHmacSignature('coqCmt/IZ4E3CzPvMY8zTjQVL5hYJUiBRg8UU+iCWo0=', hmacPayload)
		
	}
	
	@Unroll
	def "calculateHmacSignature - #testCase"() {
		
		expect:
		expectedSignature == adyenHmacUtils.calculateHmacSignature(hmacPayload)
		
		where:
		testCase | expectedSignature                              | hmacPayload
		'test 1' | 'coqCmt/IZ4E3CzPvMY8zTjQVL5hYJUiBRg8UU+iCWo0=' | '7914073381342284::TestMerchant:TestPayment-1407325143704:1130:EUR:AUTHORISATION:true'
		'test 2' | 'z5qhIY9DF1DamKAtq7VDMJBvIhHRmSdFemWjr90ZCug=' | 'VR58LFMG2SWZNN82::SquareTrade_JROSEN_TEST:811B30BF-3EA1-4BBA-AD11-78E6393A5294:1633:USD:AUTHORISATION:true'
		'test 3' | 'vc9nQUsA8roH21dr0g6y2lQGVx7dZ8yMu/wzkYAjSqM=' | 'ZGJPCLGNK75ZGN82::SquareTrade_JROSEN_TEST:D22818E1-82AB-4987-97F6-D8F068FEBF6D:1632:USD:AUTHORISATION:true'
		
	} 
}
