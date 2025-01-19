package sceaj.adyenmock.payments

import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod
import sceaj.adyenmock.paymentmethods.PaymentMethodMapperImpl
import sceaj.adyenmock.persistence.PaymentMethodTypeRepository
import sceaj.adyenmock.persistence.entities.PaymentMethodEntity
import sceaj.adyenmock.webhooks.WebhookService
import spock.lang.Specification

import static sceaj.adyenmock.test.ObjectMother.buildPaymentRequest

class CheckoutPaymentServiceSpecification extends Specification {

    def webhookService = Mock(WebhookService)
    def paymentMethodMapper = new PaymentMethodMapperImpl()
    def paymentMethodTypeRepository = Mock(PaymentMethodTypeRepository)
    def checkoutService = new CheckoutPaymentsService(webhookService, paymentMethodTypeRepository, paymentMethodMapper)

    def 'Payment Methods - find all available payment methods'() {
        given:
        PaymentMethod request = PaymentMethod.builder().merchantAccount("merchant_account").build()

        and:
        PaymentMethodEntity paymentMethodEntity = new PaymentMethodEntity()
        paymentMethodEntity.paymentMethodId = 1
        paymentMethodEntity.name = 'Cards'
        paymentMethodEntity.type = 'schemes'
        paymentMethodTypeRepository.findAll() >> [paymentMethodEntity]

        when:
        def result = checkoutService.getAvailablePaymentMethods(request)

        then:
        result != null
        result.size() == 1
        result[0].name == 'Cards'
        result[0].type == 'schemes'
    }

    def 'Payments - process new payment'() {
        given:
        def request = buildPaymentRequest()

        when:
        def result = checkoutService.processPayment(request)

        then:
        result.merchantReference == 'merchant_account_reference'
        result.pspReference == '123456'
        result.resultCode == 'Authorized'
        result.additionalData.authCode == '065696'
        result.additionalData.refusalReasonRaw == 'AUTHORIZED'

        and:
        1 * webhookService.createWebhook(_)
    }
}
