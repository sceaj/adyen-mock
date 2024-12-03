package sceaj.adyenmock.api.v1.model.payment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Amount {

    private String currency;
    private Long value;
}
