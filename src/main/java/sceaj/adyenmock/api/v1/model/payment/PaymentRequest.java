package sceaj.adyenmock.api.v1.model.payment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentRequest {

    private Amount amount;
    private String merchantAccount;
    private RequestPaymentMethod paymentMethod;
    private String reference;
    private String returnUrl;
}
