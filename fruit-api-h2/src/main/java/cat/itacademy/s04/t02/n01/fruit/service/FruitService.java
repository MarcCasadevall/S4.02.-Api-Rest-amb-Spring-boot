package cat.itacademy.s04.t02.n01.fruit.service;

import cat.itacademy.s04.t02.n01.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n01.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n01.fruit.model.Fruit;
import cat.itacademy.s04.t02.n01.fruit.model.Provider;
import cat.itacademy.s04.t02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n01.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;
    private final ProviderRepository providerRepository;

    public FruitService(FruitRepository fruitRepository, ProviderRepository providerRepository) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
    }

    public FruitResponseDTO createFruit(FruitRequestDTO requestDTO) {
        Provider provider = providerRepository.findById(requestDTO.getProviderId())
                .orElseThrow(() -> new ProviderNotFoundException(requestDTO.getProviderId()));
        Fruit fruit = new Fruit(null, requestDTO.getName(), requestDTO.getWeightKg(), provider);
        Fruit savedFruit = fruitRepository.save(fruit);
        return toResponseDTO(savedFruit);
    }

    public List<FruitResponseDTO> getAllFruits() {
        return fruitRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public FruitResponseDTO getFruitById(Long id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new FruitNotFoundException(id));
        return toResponseDTO(fruit);
    }

    public FruitResponseDTO updateFruit(Long id, FruitRequestDTO requestDTO) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new FruitNotFoundException(id));
        Provider provider = providerRepository.findById(requestDTO.getProviderId())
                .orElseThrow(() -> new ProviderNotFoundException(requestDTO.getProviderId()));
        fruit.setName(requestDTO.getName());
        fruit.setWeightKg(requestDTO.getWeightKg());
        fruit.setProvider(provider);
        Fruit updatedFruit = fruitRepository.save(fruit);
        return toResponseDTO(updatedFruit);
    }

    public void deleteFruit(Long id) {
        fruitRepository.findById(id)
                .orElseThrow(() -> new FruitNotFoundException(id));
        fruitRepository.deleteById(id);
    }

    private FruitResponseDTO toResponseDTO(Fruit fruit) {
        return new FruitResponseDTO(fruit.getId(), fruit.getName(), fruit.getWeightKg());
    }

    public List<FruitResponseDTO> getFruitsByProvider(Long providerId) {
        providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException(providerId));
        return fruitRepository.findByProviderId(providerId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
}