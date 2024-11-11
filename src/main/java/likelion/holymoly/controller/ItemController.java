package likelion.holymoly.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/item")
@Tag(name = "아이템 조회", description = "아이템 리스트 조회 API")
public class ItemController {

    @Operation(summary = "배경 소품 리스트 조회", description = "모든 배경 소품 정보를 조회합니다.")
    @GetMapping("/bg")
    public ResponseEntity<String> getAllBackgroundItems() {
        // 추후 입력
        return ResponseEntity.ok("모든 배경 소품 조회 성공");
    }

    @Operation(summary = "머리 소품 리스트 조회", description = "모든 머리 소품 정보를 조회합니다.")
    @GetMapping("/head")
    public ResponseEntity<String> getAllHeadItems() {
        // 추후 입력
        return ResponseEntity.ok("모든 머리 소품 조회 성공");
    }

    @Operation(summary = "얼굴 소품 리스트 조회", description = "모든 얼굴 소품 정보를 조회합니다.")
    @GetMapping("/face")
    public ResponseEntity<String> getAllFaceItems() {
        // 추후 입력
        return ResponseEntity.ok("모든 얼굴 소품 조회 성공");
    }

    @Operation(summary = "옷 소품 리스트 조회", description = "모든 옷 소품 정보를 조회합니다.")
    @GetMapping("/clothes")
    public ResponseEntity<String> getAllClothesItems() {
        // 추후 입력
        return ResponseEntity.ok("모든 옷 소품 조회 성공");
    }

    @Operation(summary = "액세서리 소품 리스트 조회", description = "모든 액세서리 소품 정보를 조회합니다.")
    @GetMapping("/accessory")
    public ResponseEntity<String> getAllAccessoryItems() {
        // 추후 입력
        return ResponseEntity.ok("모든 액세서리 소품 조회 성공");
    }
}
