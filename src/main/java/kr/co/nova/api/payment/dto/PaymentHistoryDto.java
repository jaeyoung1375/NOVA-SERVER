package kr.co.nova.api.payment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentHistoryDto {
    private Long userId;
    private String pgTransactionId; // orderId
    private String paymentKey;
    private Long amount;
    private String status;          // READY, DONE, FAILED, CANCELED
    private LocalDateTime approvedAt; // 승인 안됐으면 null
    private String rawResponse;     // Toss 응답 JSON 문자열
    private String regId;
}
