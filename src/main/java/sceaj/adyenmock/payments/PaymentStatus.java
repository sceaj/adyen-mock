package sceaj.adyenmock.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {

    AUTHORIZED("Authorized");

    private final String description;
}
