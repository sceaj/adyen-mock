package sceaj.adyenmock.api.v1.model.paymentmethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PaymentMethod {

    private String name;
    private String type;
}
