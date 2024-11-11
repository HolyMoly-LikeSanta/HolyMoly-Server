package likelion.holymoly.service;

import likelion.holymoly.dto.*;
import likelion.holymoly.entity.Background;
import likelion.holymoly.entity.Head;
import likelion.holymoly.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final BackgroundRepository backgroundRepository;
    private final HeadRepository headRepository;
    private final FaceRepository faceRepository;
    private final ClothesRepository clothesRepository;
    private final AccessoryRepository accessoryRepository;

    public List<BackgroundDto> getAllBackgroundItems() {
        List<Background> backgrounds = backgroundRepository.findAll();
        return backgrounds.stream()
                .map(BackgroundDto::fromEntity)  // Using the DTO conversion method here
                .collect(Collectors.toList());
    }

    public List<HeadDto> getAllHeadItems() {
        List<Head> heads = headRepository.findAll();
        return heads.stream()
                .map(HeadDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<FaceDto> getAllFaceItems() {
        return faceRepository.findAll().stream()
                .map(FaceDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ClothesDto> getAllClothesItems() {
        return clothesRepository.findAll().stream()
                .map(ClothesDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AccessoryDto> getAllAccessoryItems() {
        return accessoryRepository.findAll().stream()
                .map(AccessoryDto::fromEntity)
                .collect(Collectors.toList());
    }
}
