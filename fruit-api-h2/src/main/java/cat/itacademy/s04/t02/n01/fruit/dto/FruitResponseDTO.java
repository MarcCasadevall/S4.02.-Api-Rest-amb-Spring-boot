package cat.itacademy.s04.t02.n01.fruit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FruitResponseDTO {

    private Long id;
    private String name;
    private double weightKg;
}