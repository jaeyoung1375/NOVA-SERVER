package kr.co.nova.concert.service;

import kr.co.nova.concert.dto.ConcertListDto;
import kr.co.nova.concert.mapper.ConcertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertMapper concertMapper;

    public List<ConcertListDto> getConcertList(){
        return concertMapper.getConcertList();
    }
}
