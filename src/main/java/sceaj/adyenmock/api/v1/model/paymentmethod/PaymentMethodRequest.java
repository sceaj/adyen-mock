package sceaj.adyenmock.api.v1.model.paymentmethod;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentMethodRequest {

    private String merchantAccount;

}
