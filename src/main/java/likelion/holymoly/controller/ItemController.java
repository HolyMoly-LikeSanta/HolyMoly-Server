package likelion.holymoly.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.holymoly.dto.*;
import likelion.holymoly.service.ItemService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/item")
@Tag(name = "아이템 조회", description = "아이템 리스트 조회 API")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "배경 소품 리스트 조회", description = "모든 배경 소품 정보를 조회합니다.")
    @GetMapping("/bg")
    public ResponseEntity<List<BackgroundDto>> getAllBackgroundItems() {
        List<BackgroundDto> backgrounds = itemService.getAllBackgroundItems();
        return ResponseEntity.ok(backgrounds);
    }

    @Operation(summary = "머리 소품 리스트 조회", description = "모든 머리 소품 정보를 조회합니다.")
    @GetMapping("/head")
    public ResponseEntity<List<HeadDto>> getAllHeadItems() {
        List<HeadDto> heads = itemService.getAllHeadItems();
        return ResponseEntity.ok(heads);
    }

    @Operation(summary = "얼굴 소품 리스트 조회", description = "모든 얼굴 소품 정보를 조회합니다.")
    @GetMapping("/face")
    public ResponseEntity<List<FaceDto>> getAllFaceItems() {
        List<FaceDto> faces = itemService.getAllFaceItems();
        return ResponseEntity.ok(faces);
    }

    @Operation(summary = "옷 소품 리스트 조회", description = "모든 옷 소품 정보를 조회합니다.")
    @GetMapping("/clothes")
    public ResponseEntity<List<ClothesDto>> getAllClothesItems() {
        List<ClothesDto> clothes = itemService.getAllClothesItems();
        return ResponseEntity.ok(clothes);
    }

    @Operation(summary = "액세서리 소품 리스트 조회", description = "모든 액세서리 소품 정보를 조회합니다.")
    @GetMapping("/accessory")
    public ResponseEntity<List<AccessoryDto>> getAllAccessoryItems() {
        List<AccessoryDto> accessories = itemService.getAllAccessoryItems();
        return ResponseEntity.ok(accessories);
    }
}
