package kr.co.nova.admin.concert.service;

import kr.co.nova.admin.concert.dto.AdminConcertListDto;
import kr.co.nova.admin.concert.mapper.AdminConcertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminConcertService {

    private final AdminConcertMapper concertMapper;

    public List<AdminConcertListDto> getConcertList(){
        return concertMapper.getConcertList();
    }
}
