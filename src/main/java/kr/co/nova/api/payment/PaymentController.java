package kr.co.nova.api.payment;

import kr.co.nova.api.payment.dto.PaymentConfirmDto;
import kr.co.nova.api.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/success")
    public void success(@RequestBody PaymentConfirmDto confirmDto){
        paymentService.onSuccess(confirmDto);
    }
}
