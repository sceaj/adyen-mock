package sceaj.adyenmock.api.v1.model.payment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestPaymentMethod {

    private String type;
    private String encryptedCardNumber;
    private String encryptedExpiryMonth;
    private String encryptedExpiryYear;
    private String encryptedSecurityCode;
}
