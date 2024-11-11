package likelion.holymoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import likelion.holymoly.entity.Accessory;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryDto {
    private Long accessoryId;
    private String name;
    private String imageUrl;
    private String description;

    public static AccessoryDto fromEntity(Accessory accessory) {
        return new AccessoryDto(
                accessory.getAccessoryId(),
                accessory.getName(),
                accessory.getImageUrl(),
                accessory.getDescription()
        );
    }
}
