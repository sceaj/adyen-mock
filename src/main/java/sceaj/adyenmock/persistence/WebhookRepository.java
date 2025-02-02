package sceaj.adyenmock.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sceaj.adyenmock.persistence.entities.WebhookEntity;
import sceaj.adyenmock.webhooks.WebhookStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WebhookRepository extends JpaRepository<WebhookEntity, Long> {

    List<WebhookEntity> findAllByStatusAndDeliveryDateBetween(WebhookStatus status, LocalDateTime startDate, LocalDateTime endDate);
}
