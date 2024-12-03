package sceaj.adyenmock.payments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sceaj.adyenmock.api.v1.model.payment.AdditionalData;
import sceaj.adyenmock.api.v1.model.payment.PaymentRequest;
import sceaj.adyenmock.api.v1.model.payment.PaymentResponse;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethodRequest;

import java.util.List;

@Slf4j
@Service
public class CheckoutPaymentsService {

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        log.info("Processing the request...");
        return buildResponse();
    }

    public List<PaymentMethod> getAvailablePaymentMethods(PaymentMethodRequest request) {
        log.info("paymentMethod={}", request);
        PaymentMethod cardMethod = new PaymentMethod("Cards", "scheme");
        PaymentMethod paypalMethod = new PaymentMethod("PayPal", "paypal");
        return List.of(cardMethod, paypalMethod);
    }

    private PaymentResponse buildResponse() {
        return PaymentResponse.builder()
                .pspReference("993617895215577D")
                .resultCode("Authorised")
                .merchantReference("merchant")
                .additionalData(buildAdditionalData())
                .build();
    }

    private AdditionalData buildAdditionalData() {
        return AdditionalData.builder()
                .cvcResult("1 Matches")
                .authCode("065696")
                .avsResult("4 AVS not supported for this card type")
                .avsResultRaw("4")
                .refusalReasonRaw("AUTHORISED")
                .acquirerCode("TestPmmAcquirer")
                .acquirerReference("8PQMP9VIE9N")
                .build();
    }
}
