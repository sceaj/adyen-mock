package sceaj.adyenmock.api.v1.model.payment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentResponse {

    private AdditionalData additionalData;
    private String merchantReference;
    private String pspReference;
    private String resultCode;
}
