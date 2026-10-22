package kr.co.nova.concert.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.nova.common.response.ApiResponse;
import kr.co.nova.concert.dto.ConcertListDto;
import kr.co.nova.concert.mapper.ConcertMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "콘서트 Controller", description = "콘서트 컨트롤러")
public class ConcertController {

    private final ConcertMapper concertMapper;

    @Operation(summary = "콘서트 목록을 조회한다.")
    @GetMapping("/concerts")
    public ApiResponse<List<ConcertListDto>> getConcertList() {
        List<ConcertListDto> concertList = concertMapper.getConcertList();

        return ApiResponse.ok(concertList);
    }
}
