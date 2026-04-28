package cat.itacademy.s04.t02.n01.fruit.service;

import cat.itacademy.s04.t02.n01.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n01.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n01.fruit.model.Fruit;
import cat.itacademy.s04.t02.n01.fruit.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public FruitResponseDTO createFruit(FruitRequestDTO requestDTO) {
        Fruit fruit = new Fruit(null, requestDTO.getName(), requestDTO.getWeightKg());
        Fruit savedFruit = fruitRepository.save(fruit);
        return new FruitResponseDTO(savedFruit.getId(), savedFruit.getName(), savedFruit.getWeightKg());
    }

    public List<FruitResponseDTO> getAllFruits() {
        return fruitRepository.findAll()
                .stream()
                .map(fruit -> new FruitResponseDTO(fruit.getId(), fruit.getName(), fruit.getWeightKg()))
                .toList();
    }
}