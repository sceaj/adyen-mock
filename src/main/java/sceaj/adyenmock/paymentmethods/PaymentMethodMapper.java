package sceaj.adyenmock.paymentmethods;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod;
import sceaj.adyenmock.persistence.entities.PaymentMethodEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMethodMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "type", target = "type")
    @Mapping(target = "merchantAccount", ignore = true)
    @Mapping(target = "encryptedCardNumber", ignore = true)
    @Mapping(target = "encryptedExpiryMonth", ignore = true)
    @Mapping(target = "encryptedExpiryYear", ignore = true)
    @Mapping(target = "encryptedSecurityCode", ignore = true)
    PaymentMethod fromEntity(PaymentMethodEntity paymentMethodEntity);
}
