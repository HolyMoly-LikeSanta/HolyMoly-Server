package likelion.holymoly.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import likelion.holymoly.entity.Mission;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionDto {
    private Long missionId;
    private String name;
    private String description;

    // Entity -> DTO 변환 메서드
    public static MissionDto fromEntity(Mission mission) {
        return new MissionDto(
                mission.getMissionId(),
                mission.getName(),
                mission.getDescription()
        );
    }
}
