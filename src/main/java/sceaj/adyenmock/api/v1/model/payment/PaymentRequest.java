package sceaj.adyenmock.api.v1.model.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private Amount amount;
    private String merchantAccount;
    private PaymentMethod paymentMethod;
    private String reference;
    private String returnUrl;
}
