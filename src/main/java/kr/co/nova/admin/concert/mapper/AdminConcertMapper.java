package kr.co.nova.admin.concert.mapper;

import kr.co.nova.admin.concert.dto.AdminConcertListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminConcertMapper {

    List<AdminConcertListDto> getConcertList();
}
