package cat.itacademy.s04.t02.n01.fruit.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotBlank
    private String clientName;

    @NotNull
    @Future
    private LocalDate deliveryDate;

    @NotNull
    @NotEmpty
    private List<OrderItemDTO> items;
}