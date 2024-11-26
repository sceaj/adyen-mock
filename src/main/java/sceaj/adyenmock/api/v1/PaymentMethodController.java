package sceaj.adyenmock.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethodRequest;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod;
import sceaj.adyenmock.paymentmethod.PaymentMethodService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/paymentMethods", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentMethod> getAvailablePaymentMethods(PaymentMethodRequest request) {
        log.info("Find all available payment methods for merchantAccount={}", request.getMerchantAccount());
        return paymentMethodService.getAvailablePaymentMethods(request);
    }
}
