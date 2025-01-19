package sceaj.adyenmock.api.v1.model.webhook;

import lombok.Builder;
import lombok.Data;
import sceaj.adyenmock.api.v1.model.payment.Amount;

@Data
@Builder
public class NotificationRequestItem {

    private String eventCode;
    private String eventDate;
    private String merchantAccountCode;
    private String merchantReference;
    private String paymentMethod;
    private String pspReference;
    private String reason;
    private String success;
    private Amount amount;

}
