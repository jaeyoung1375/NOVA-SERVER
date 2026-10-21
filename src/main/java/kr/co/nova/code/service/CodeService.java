package kr.co.nova.code.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import kr.co.nova.code.dto.CodeRequestDto;
import kr.co.nova.code.dto.CodeResponseDto;
import kr.co.nova.code.mapper.CodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeService {

	private final CodeMapper codeMapper;

	/**
	 * 코드 다건 조회
	 * @param comCdIds
	 * @return
	 */
	public Map<String,List<CodeResponseDto>> getCodeList(CodeRequestDto dto){

		List<CodeResponseDto> codes = codeMapper.getCodeList(dto);

		 return codes.stream()
				 .collect(Collectors.groupingBy(CodeResponseDto::getComCdId));
	}

	/**
	 * 코드 단건 조회
	 * @param comCdIds
	 * @return
	 */
	public List<CodeResponseDto> getCode(CodeRequestDto dto){

		List<CodeResponseDto> codes = codeMapper.getCodeList(dto);

		return codes;



	}

}
