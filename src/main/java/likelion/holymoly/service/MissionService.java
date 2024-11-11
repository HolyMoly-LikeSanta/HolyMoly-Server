package likelion.holymoly.service;

import likelion.holymoly.dto.MissionDto;
import likelion.holymoly.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    public List<MissionDto> getAllMissions() {
        return missionRepository.findAll().stream()
                .map(MissionDto::fromEntity)
                .collect(Collectors.toList());
    }
}
