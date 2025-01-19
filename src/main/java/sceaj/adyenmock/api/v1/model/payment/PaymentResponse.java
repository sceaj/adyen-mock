package sceaj.adyenmock.api.v1.model.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private AdditionalData additionalData;
    private String merchantReference;
    private String pspReference;
    private String resultCode;
}
