package sceaj.adyenmock.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sceaj.adyenmock.api.v1.model.payment.PaymentRequest;
import sceaj.adyenmock.api.v1.model.payment.PaymentResponse;
import sceaj.adyenmock.payments.PaymentService;

@Slf4j
@RestController
@RequestMapping(path = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PaymentResponse acceptPayment(@RequestBody PaymentRequest request) {
        log.info("Payment request received: {}", request);
        return paymentService.processPayment(request);
    }


}

