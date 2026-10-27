package kr.co.nova.admin.concert.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.nova.admin.concert.dto.AdminConcertListDto;
import kr.co.nova.admin.concert.mapper.AdminConcertMapper;
import kr.co.nova.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "관리자 콘서트 Controller", description = "관리자 콘서트 컨트롤러")
public class AdminConcertController {

    private final AdminConcertMapper concertMapper;

    @Operation(summary = "콘서트 목록을 조회한다.")
    @GetMapping("/concerts")
    public ApiResponse<List<AdminConcertListDto>> getConcertList() {
        List<AdminConcertListDto> concertList = concertMapper.getConcertList();

        return ApiResponse.ok(concertList);
    }
}
