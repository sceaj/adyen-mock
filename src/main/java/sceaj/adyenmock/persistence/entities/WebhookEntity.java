package sceaj.adyenmock.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import sceaj.adyenmock.webhooks.WebhookStatus;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "webhooks")
public class WebhookEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long webhookId;

    @NotBlank
    private String payload;

    @NotNull
    private LocalDateTime deliveryDate;

    @Enumerated(EnumType.STRING)
    private WebhookStatus status;

    private Integer retries;

}
