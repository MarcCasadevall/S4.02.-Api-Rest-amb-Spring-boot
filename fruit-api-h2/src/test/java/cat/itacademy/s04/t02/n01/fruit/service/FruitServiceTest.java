package cat.itacademy.s04.t02.n01.fruit.service;

import cat.itacademy.s04.t02.n01.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n01.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruit.model.Fruit;
import cat.itacademy.s04.t02.n01.fruit.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FruitServiceTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitService fruitService;

    @Test
    void createFruit_shouldReturnFruitResponseDTO() {
        FruitRequestDTO requestDTO = new FruitRequestDTO("Apple", 1.5);
        Fruit savedFruit = new Fruit(1L, "Apple", 1.5);

        when(fruitRepository.save(any(Fruit.class))).thenReturn(savedFruit);

        FruitResponseDTO responseDTO = fruitService.createFruit(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Apple", responseDTO.getName());
        assertEquals(1.5, responseDTO.getWeightKg());
    }
    @Test
    void getAllFruits_shouldReturnListOfFruitResponseDTO() {
        List<Fruit> fruits = List.of(
                new Fruit(1L, "Apple", 1.5),
                new Fruit(2L, "Banana", 0.8)
        );

        when(fruitRepository.findAll()).thenReturn(fruits);

        List<FruitResponseDTO> result = fruitService.getAllFruits();

        assertEquals(2, result.size());
        assertEquals("Apple", result.get(0).getName());
        assertEquals("Banana", result.get(1).getName());
    }
    @Test
    void getFruitById_withExistingId_shouldReturnFruitResponseDTO() {
        Fruit fruit = new Fruit(1L, "Apple", 1.5);

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));

        FruitResponseDTO result = fruitService.getFruitById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Apple", result.getName());
        assertEquals(1.5, result.getWeightKg());
    }

    @Test
    void getFruitById_withNonExistingId_shouldThrowFruitNotFoundException() {
        when(fruitRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.getFruitById(99L));
    }

    @Test
    void updateFruit_withExistingId_shouldReturnUpdatedFruitResponseDTO() {
        FruitRequestDTO requestDTO = new FruitRequestDTO("Mango", 2.0);
        Fruit existingFruit = new Fruit(1L, "Apple", 1.5);
        Fruit updatedFruit = new Fruit(1L, "Mango", 2.0);

        when(fruitRepository.findById(1L)).thenReturn(Optional.of(existingFruit));
        when(fruitRepository.save(any(Fruit.class))).thenReturn(updatedFruit);

        FruitResponseDTO result = fruitService.updateFruit(1L, requestDTO);

        assertNotNull(result);
        assertEquals("Mango", result.getName());
        assertEquals(2.0, result.getWeightKg());
    }

    @Test
    void updateFruit_withNonExistingId_shouldThrowFruitNotFoundException() {
        FruitRequestDTO requestDTO = new FruitRequestDTO("Mango", 2.0);

        when(fruitRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.updateFruit(99L, requestDTO));
    }
}