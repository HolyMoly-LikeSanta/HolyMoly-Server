package likelion.holymoly.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.holymoly.dto.MissionDto;
import likelion.holymoly.service.MissionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/mission")
@Tag(name = "미션 관리", description = "미션 정보 조회 API")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @Operation(summary = "미션 정보 리스트 조회", description = "모든 미션 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<MissionDto>> getAllMissions() {
        List<MissionDto> missions = missionService.getAllMissions();
        return ResponseEntity.ok(missions);
    }
}
