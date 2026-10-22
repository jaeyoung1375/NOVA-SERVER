package kr.co.nova.venue.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenuDto {

    @Schema(description = "공연장ID")
    private Long venueId;

    @Schema(description = "공연장명")
    private String venueNm;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "등록자")
    private String regId;

    @Schema(description = "등록일시")
    private LocalDateTime regDt;

    @Schema(description = "수정자")
    private String modId;

    @Schema(description = "수정일시")
    private LocalDateTime modDt;

}
