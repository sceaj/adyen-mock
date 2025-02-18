package sceaj.adyenmock.test

import sceaj.adyenmock.api.v1.model.Card
import sceaj.adyenmock.api.v1.model.payment.Amount
import sceaj.adyenmock.api.v1.model.payment.PaymentRequest
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod

class ObjectMother {

    static buildPaymentRequest() {
        PaymentRequest request = new PaymentRequest()
        request.merchantAccount = 'merchant_account_reference'
        request.reference = '123456'
        request.returnUrl = 'http://localhost:8080'
        request.amount = new Amount("CAD", 1000)
        request.paymentMethod = buildRequestPaymentMethod()
        request
    }

    static buildRequestPaymentMethod() {
        PaymentMethod request = new PaymentMethod()
        request.type = 'scheme'
        request.encryptedCardNumber = '4111111111111111'
        request.encryptedExpiryMonth = '03'
        request.encryptedExpiryYear = '2030'
        request.encryptedSecurityCode = '737'
        request
    }

    static buildCard(props = null) {
        applyProperties(props, new Card(
                externalKey: UUID.randomUUID(),
                '4444-5555-6666-8291',
                'John Doe',
                '6',
                '2026',
                '372'
        ))
    }

    static <T> T applyProperties(props, T object) {
        if (props) {
            props.each { k, v -> object[k] = v }
        }
        object
    }
}
