package by.training.machine.monitoring.manufacture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufactureDto {
    private Long id;
    private String name;
    private Long userId;
}
