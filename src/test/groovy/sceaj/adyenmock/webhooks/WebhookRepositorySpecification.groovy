package sceaj.adyenmock.webhooks

import org.springframework.beans.factory.annotation.Autowired
import sceaj.adyenmock.SpringContextSpecification
import sceaj.adyenmock.persistence.WebhookRepository
import sceaj.adyenmock.persistence.entities.WebhookEntity
import spock.lang.Subject

import java.time.LocalDateTime

class WebhookRepositorySpecification extends SpringContextSpecification {

    @Autowired
    @Subject
    WebhookRepository repository;

    def 'Webhooks - save'() {
        given:
        def entity = new WebhookEntity()
        entity.deliveryDate = LocalDateTime.of(2025, 1, 1, 12, 1, 30)
        entity.payload = 'some information in here'
        entity.status = WebhookStatus.PENDING
        entity.returnUrl = "http://localhost/confirmation"
        entity.retries = 0

        when:
        def savedWebhook = repository.save(entity)

        then:
        savedWebhook != null
        savedWebhook.webhookId == 1
    }

    def 'Webhooks - find all pending webhooks'() {
        given:
        def status = WebhookStatus.PENDING
        def startDate = LocalDateTime.of(2025, 1, 1, 12, 1, 0)
        def endDate = LocalDateTime.of(2025, 1, 1, 12, 2, 0)

        when:
        def webhooks = repository.findAllByStatusAndDeliveryDateBetween(status, startDate, endDate)

        then:
        webhooks != null
        webhooks.size() > 0
    }
}
