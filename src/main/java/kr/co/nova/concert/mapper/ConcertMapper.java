package kr.co.nova.concert.mapper;

import kr.co.nova.concert.dto.ConcertListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConcertMapper {

    List<ConcertListDto> getConcertList();
}
