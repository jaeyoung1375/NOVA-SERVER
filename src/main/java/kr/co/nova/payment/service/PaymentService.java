package kr.co.nova.payment.service;

import kr.co.nova.auth.util.JwtTokenUtil;
import kr.co.nova.common.util.JsonUtil;
import kr.co.nova.common.util.SecurityUtil;
import kr.co.nova.payment.dto.PaymentConfirmDto;
import kr.co.nova.payment.dto.PaymentHistoryDto;
import kr.co.nova.payment.mapper.PaymentMapper;
import kr.co.nova.toss.dto.TossConfirmResultDto;
import kr.co.nova.toss.service.TossPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentMapper paymentMapper;
    private final TossPaymentService tossPaymentService;

    public void onSuccess(PaymentConfirmDto confirmDto) {

        Long userId = SecurityUtil.getUserId();

        String rawJson = tossPaymentService.confirm(confirmDto);

        TossConfirmResultDto resultDto = JsonUtil.fromJson(rawJson, TossConfirmResultDto.class);

        PaymentHistoryDto historyDto = PaymentHistoryDto
                .builder()
                .pgTransactionId(resultDto.getOrderId())
                .paymentKey(resultDto.getPaymentKey())
                .status(resultDto.getStatus())
                .approvedAt(resultDto.getApprovedAt())
                .rawResponse(rawJson)
                .userId(userId)
                .amount(confirmDto.getAmount())
                .build();

        paymentMapper.insertPaymentHistory(historyDto);
    }
}
