package sceaj.adyenmock.payments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sceaj.adyenmock.api.v1.model.payment.AdditionalData;
import sceaj.adyenmock.api.v1.model.payment.PaymentRequest;
import sceaj.adyenmock.api.v1.model.payment.PaymentResponse;
import sceaj.adyenmock.api.v1.model.paymentmethod.PaymentMethod;
import sceaj.adyenmock.paymentmethods.PaymentMethodMapper;
import sceaj.adyenmock.persistence.PaymentMethodTypeRepository;
import sceaj.adyenmock.persistence.entities.PaymentMethodEntity;
import sceaj.adyenmock.webhooks.WebhookService;

import java.util.List;

@Slf4j
@Service
public class CheckoutPaymentsService {

    private final WebhookService webhookService;
    private final PaymentMethodTypeRepository paymentMethodTypeRepository;
    private final PaymentMethodMapper paymentMethodTypeMapper;

    public CheckoutPaymentsService(WebhookService webhookService, PaymentMethodTypeRepository paymentMethodTypeRepository, PaymentMethodMapper paymentMethodTypeMapper) {
        this.webhookService = webhookService;
        this.paymentMethodTypeRepository = paymentMethodTypeRepository;
        this.paymentMethodTypeMapper = paymentMethodTypeMapper;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing payment request {}", request.getReference());
        webhookService.createWebhook(request);
        return buildResponse(request);
    }

    public List<PaymentMethod> getAvailablePaymentMethods(PaymentMethod request) {
        log.info("Retrieving payment methods configured for merchant={}", request.getMerchantAccount());
        List<PaymentMethodEntity> paymentMethodTypeEntities = paymentMethodTypeRepository.findAll();
        return paymentMethodTypeEntities.stream().map(paymentMethodTypeMapper::fromEntity).toList();
    }

    private PaymentResponse buildResponse(PaymentRequest request) {
        return PaymentResponse.builder()
                .pspReference(request.getReference())
                .resultCode(PaymentStatus.AUTHORIZED.getDescription())
                .merchantReference(request.getMerchantAccount())
                .additionalData(buildAdditionalData())
                .build();
    }

    private AdditionalData buildAdditionalData() {
        return AdditionalData.builder()
                .cvcResult("1 Matches")
                .authCode("065696")
                .avsResult("4 AVS not supported for this card type")
                .avsResultRaw("4")
                .refusalReasonRaw(PaymentStatus.AUTHORIZED.name())
                .acquirerCode("TestPmmAcquirer")
                .acquirerReference("8PQMP9VIE9N")
                .build();
    }
}
