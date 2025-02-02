package sceaj.adyenmock.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sceaj.adyenmock.api.v1.model.payment.PaymentRequest;
import sceaj.adyenmock.api.v1.model.payment.PaymentResponse;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod;
import sceaj.adyenmock.payments.CheckoutPaymentsService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/v71", produces = MediaType.APPLICATION_JSON_VALUE)
public class CheckoutPaymentsController {

    private final CheckoutPaymentsService checkoutPaymentsService;

    public CheckoutPaymentsController(CheckoutPaymentsService checkoutPaymentsService) {
        this.checkoutPaymentsService = checkoutPaymentsService;
    }

    @PostMapping(path = "/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PaymentResponse acceptPayment(@RequestBody PaymentRequest request) throws JsonProcessingException {
        log.info("Payment request received: {}", request);
        return checkoutPaymentsService.processPayment(request);
    }

    @PostMapping(path = "/paymentMethods", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentMethod> getAvailablePaymentMethods(@RequestBody PaymentMethod request) {
        log.info("Find all available payment methods for merchantAccount={}", request.getMerchantAccount());
        return checkoutPaymentsService.getAvailablePaymentMethods(request);
    }

}

