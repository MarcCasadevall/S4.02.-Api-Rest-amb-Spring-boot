package cat.itacademy.s04.t02.n01.fruit.service;

import cat.itacademy.s04.t02.n01.fruit.dto.ProviderRequestDTO;
import cat.itacademy.s04.t02.n01.fruit.dto.ProviderResponseDTO;
import cat.itacademy.s04.t02.n01.fruit.exception.ProviderHasFruitsException;
import cat.itacademy.s04.t02.n01.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.t02.n01.fruit.model.Fruit;
import cat.itacademy.s04.t02.n01.fruit.model.Provider;
import cat.itacademy.s04.t02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.t02.n01.fruit.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final FruitRepository fruitRepository;

    public ProviderService(ProviderRepository providerRepository, FruitRepository fruitRepository) {
        this.providerRepository = providerRepository;
        this.fruitRepository = fruitRepository;
    }

    public ProviderResponseDTO createProvider(ProviderRequestDTO requestDTO) {
        Provider provider = new Provider(null, requestDTO.getName(), requestDTO.getCountry());
        Provider savedProvider = providerRepository.save(provider);
        return new ProviderResponseDTO(savedProvider.getId(), savedProvider.getName(), savedProvider.getCountry());
    }

    public List<ProviderResponseDTO> getAllProviders() {
        return providerRepository.findAll()
                .stream()
                .map(provider -> new ProviderResponseDTO(provider.getId(), provider.getName(), provider.getCountry()))
                .toList();
    }

    public ProviderResponseDTO updateProvider(Long id, ProviderRequestDTO requestDTO) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(id));
        provider.setName(requestDTO.getName());
        provider.setCountry(requestDTO.getCountry());
        Provider updatedProvider = providerRepository.save(provider);
        return new ProviderResponseDTO(updatedProvider.getId(), updatedProvider.getName(), updatedProvider.getCountry());
    }

    public void deleteProvider(Long id) {
        providerRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException(id));
        List<Fruit> fruits = fruitRepository.findByProviderId(id);
        if (!fruits.isEmpty()) {
            throw new ProviderHasFruitsException(id);
        }
        providerRepository.deleteById(id);
    }
}