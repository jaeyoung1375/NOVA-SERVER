package kr.co.nova.payment.mapper;

import kr.co.nova.payment.dto.PaymentHistoryDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    int insertPaymentHistory(PaymentHistoryDto dto);
}
