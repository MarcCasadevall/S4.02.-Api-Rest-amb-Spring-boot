package cat.itacademy.s04.t02.n01.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FruitRequestDTO {

    @NotBlank
    private String name;

    @Positive
    private double weightKg;
}