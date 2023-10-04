package sceaj.adyenmock.utils

import sceaj.adyenmock.utils.CardNumberUtils
import spock.lang.Specification
import spock.lang.Unroll

class CardNumberUtilsSpecification extends Specification {
	
	def "Luhn digit calculation testCase"(int[] testPayload, int expectedCheckDigit) {
		
		expect:
		expectedCheckDigit == CardNumberUtils.checkDigit(testPayload)
		
		where:
		testCase       | testPayload                       | expectedCheckDigit
		'test case: 1' | [1,2,3,4,5,6,7]                   | 4
		'test case: 2' | [2,0,2,1,0,9,1,7,1,3,1,3,4,7,0]   | 8
		'test case: 3' | [5,0,3,6,1,9,8,9,3,5,6,7,3,8,2]   | 1
		'test case: 4' | [4,0,4,2,5,6,4,9,8,3,6,5,2,0,0]   | 6
		'test case: 5' | [4,7,4,7,2,0,2,2,0,4,4,6,6,5,6]   | 1
		'test case: 6' | [5,1,3,7,8,6,3,0,1,2,4,0,9,0,1]   | 3
		'test case: 7' | [4,9,3,6,1,9,8,9,3,5,2,7,3,8,2]   | 4
	}
	
	def "Validate Luhn testCase"(int[] testPayload, boolean expectedResult) {
		
		expect:
		expectedResult == CardNumberUtils.luhnCheck(testPayload)
		
		where:
		testCase       | testPayload                         | expectedResult
		'test case: 1' | [1,2,3,4,5,6,7,4]                   | true
		'test case: 2' | [2,0,2,1,0,9,1,7,1,3,1,3,4,7,0,8]   | true
		'test case: 3' | [5,0,3,6,1,9,8,9,3,5,6,7,3,8,2,3]   | false
		'test case: 4' | [4,0,4,2,5,6,4,9,8,3,6,5,2,0,0,6]   | true
		'test case: 5' | [4,7,4,7,2,0,2,2,0,4,4,6,6,5,6,1]   | true
		'test case: 6' | [5,1,3,7,8,6,3,0,1,2,4,0,9,0,1,9]   | false
		'test case: 7' | [4,9,3,6,1,9,8,9,3,5,2,7,3,8,2,4]   | true
	}

	def "Validate Luhn testCase"(String testPayload, boolean expectedResult) {
	
		expect:
		expectedResult == CardNumberUtils.luhnCheck(testPayload)
	
		where:
		testCase       | testPayload          | expectedResult
		'test case: 1' | '12345674'           | true
		'test case: 2' | '2021091713134708'   | true
		'test case: 3' | '5036198935673823'   | false
		'test case: 4' | '4042564983652006'   | true
		'test case: 5' | '4747202204466561'   | true
		'test case: 6' | '5137863012409019'   | false
		'test case: 7' | '4936198935273824'   | true
	}
}
