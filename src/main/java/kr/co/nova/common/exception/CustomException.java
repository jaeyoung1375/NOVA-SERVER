package kr.co.nova.common.exception;

import kr.co.nova.common.code.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

	 private final ResponseCode responseCode;
}
