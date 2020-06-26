package by.training.machine.monitoring.model;

import by.training.machine.monitoring.entity.ModelEntity;

public class ModelUtil {
    public static ModelEntity fromDto(ModelDto modelDto) {
        return ModelEntity.builder()
                .id(modelDto.getId())
                .name(modelDto.getName())
                .releaseDate(modelDto.getReleaseDate())
                .picture(modelDto.getPicture())
                .description(modelDto.getDescription())
                .manufactureId(modelDto.getManufactureId())
                .build();
    }

    public static ModelDto fromModelEntity(ModelEntity modelEntity) {
        return ModelDto.builder()
                .id(modelEntity.getId())
                .name(modelEntity.getName())
                .releaseDate(modelEntity.getReleaseDate())
                .picture(modelEntity.getPicture())
                .description(modelEntity.getDescription())
                .manufactureId(modelEntity.getManufactureId())
                .build();
    }
}
