package sceaj.adyenmock.paymentmethods;

import org.mapstruct.Mapper;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod;
import sceaj.adyenmock.persistence.entities.PaymentMethodEntity;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethod fromEntity(PaymentMethodEntity paymentMethodEntity);
}
