package kr.co.nova.payment.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentConfirmDto {

    private String orderId;

    private String paymentKey;

    private Long amount;
}
