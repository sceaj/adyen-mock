package sceaj.adyenmock.api.v1.model.payment;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdditionalData {

    private String cvcResult;
    private String authCode;
    private String avsResult;
    private String avsResultRaw;
    private String cvcResultRaw;
    private String refusalReasonRaw;
    private String acquirerCode;
    private String acquirerReference;

}
