package likelion.holymoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import likelion.holymoly.entity.Clothes;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClothesDto {
    private Long clothesId;
    private String name;
    private String imageUrl;
    private String color;

    public static ClothesDto fromEntity(Clothes clothes) {
        return new ClothesDto(
                clothes.getClothesId(),
                clothes.getName(),
                clothes.getImageUrl(),
                clothes.getColor()
        );
    }
}
