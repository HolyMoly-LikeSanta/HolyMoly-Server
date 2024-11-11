package likelion.holymoly.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/mission")
@Tag(name = "미션 관리", description = "미션 정보 조회 API")
public class MissionController {

    @Operation(summary = "미션 정보 리스트 조회", description = "모든 미션 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<String> getAllMissions() {
        // 추후 입력
        return ResponseEntity.ok("모든 미션 정보 조회 성공");
    }
}
