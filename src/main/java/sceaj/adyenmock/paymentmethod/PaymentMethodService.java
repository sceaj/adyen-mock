package sceaj.adyenmock.paymentmethod;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethodRequest;

import java.util.List;

@Slf4j
@Service
public class PaymentMethodService {

    public List<PaymentMethod> getAvailablePaymentMethods(PaymentMethodRequest request) {
        log.info("paymentMethod={}", request);
        PaymentMethod cardMethod = new PaymentMethod("Cards", "scheme");
        PaymentMethod paypalMethod = new PaymentMethod("PayPal", "paypal");
        return List.of(cardMethod, paypalMethod);
    }
}
