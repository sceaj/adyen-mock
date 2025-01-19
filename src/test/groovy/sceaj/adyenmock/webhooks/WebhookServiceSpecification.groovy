package sceaj.adyenmock.webhooks

import sceaj.adyenmock.persistence.WebhookRepository
import spock.lang.Specification

import static sceaj.adyenmock.test.ObjectMother.buildPaymentRequest

class WebhookServiceSpecification extends Specification {

    def repository = Mock(WebhookRepository)
    def service = new WebhookService(repository)

    def 'Webhook service - create and persist webhook'() {
        given:
        def paymentRequest = buildPaymentRequest()

        when:
        service.createWebhook(paymentRequest)

        then:
        1 * repository.save(_)
    }
}
