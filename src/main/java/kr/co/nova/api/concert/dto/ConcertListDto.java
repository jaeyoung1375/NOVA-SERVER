package kr.co.nova.api.concert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertListDto {

    @Schema(description = "공연ID")
    private Long concertId;

    @Schema(description = "공연장ID")
    private Long venueId;

    @Schema(description = "공연장명")
    private String venueNm;

    @Schema(description = "공연명")
    private String title;

    @Schema(description = "포스터파일ID")
    private Long posterFileId;

    @Schema(description = "회차상태(예매 중, 마감)")
    private String saleStatus;

    @Schema(description = "예매 오픈까지 남은 일수(READY일 때만)")
    private Long daysUntilOpen;

    @Schema(description = "공연일시(1회차)")
    private LocalDate startDt;

    @Schema(description = "공연일시(N회차)")
    private LocalDate endDt;
}
