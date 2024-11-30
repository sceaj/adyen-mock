package sceaj.adyenmock.api.v1.model.paymentmethod;

import lombok.Builder;
import lombok.Data;
import sceaj.adyenmock.api.v1.model.payment.Amount;

@Builder
@Data
public class PaymentMethodRequest {

    private String merchantAccount;
    private String countryCode;
    private Amount amount;
    private String shopperReference;

}
