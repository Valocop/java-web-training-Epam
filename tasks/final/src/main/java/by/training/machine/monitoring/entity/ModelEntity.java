package by.training.machine.monitoring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelEntity {
    private Long id;
    private String name;
    private Date releaseDate;
    private String description;
    private byte[] picture;
    private Long manufactureId;
}
