package cat.itacademy.s04.t02.n01.fruit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String country;
}