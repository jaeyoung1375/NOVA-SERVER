package kr.co.nova.api.payment.mapper;

import kr.co.nova.api.payment.dto.PaymentHistoryDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    int insertPaymentHistory(PaymentHistoryDto dto);
}
