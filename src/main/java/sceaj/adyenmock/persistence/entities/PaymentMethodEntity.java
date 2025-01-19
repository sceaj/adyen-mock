package sceaj.adyenmock.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "payment_method")
public class PaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long paymentMethodId;

    @NotBlank
    @Min(3)
    @Max(50)
    private String name;

    @NotBlank
    @Min(3)
    @Max(50)
    private String type;

}
