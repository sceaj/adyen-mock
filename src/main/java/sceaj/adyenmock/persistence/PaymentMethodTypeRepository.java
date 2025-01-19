package sceaj.adyenmock.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sceaj.adyenmock.persistence.entities.PaymentMethodEntity;

@Repository
public interface PaymentMethodTypeRepository extends JpaRepository<PaymentMethodEntity, Integer> {
}
