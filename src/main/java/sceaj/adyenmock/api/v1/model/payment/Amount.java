package sceaj.adyenmock.api.v1.model.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Amount {

    private String currency;
    private Long value;
}
