package cat.itacademy.s04.t02.n01.fruit.controller;

import cat.itacademy.s04.t02.n01.fruit.dto.FruitRequestDTO;
import cat.itacademy.s04.t02.n01.fruit.dto.FruitResponseDTO;
import cat.itacademy.s04.t02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.t02.n01.fruit.service.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FruitController.class)
class FruitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FruitService fruitService;

    @Test
    void createFruit_withValidData_shouldReturn201() throws Exception {
        FruitRequestDTO requestDTO = new FruitRequestDTO("Apple", 1.5);
        FruitResponseDTO responseDTO = new FruitResponseDTO(1L, "Apple", 1.5);

        when(fruitService.createFruit(any(FruitRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightKg").value(1.5));
    }

    @Test
    void createFruit_withBlankName_shouldReturn400() throws Exception {
        FruitRequestDTO requestDTO = new FruitRequestDTO("", 1.5);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFruit_withInvalidWeight_shouldReturn400() throws Exception {
        FruitRequestDTO requestDTO = new FruitRequestDTO("Apple", -1.0);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getAllFruits_shouldReturn200WithList() throws Exception {
        List<FruitResponseDTO> responseDTOs = List.of(
                new FruitResponseDTO(1L, "Apple", 1.5),
                new FruitResponseDTO(2L, "Banana", 0.8)
        );

        when(fruitService.getAllFruits()).thenReturn(responseDTOs);

        mockMvc.perform(get("/fruits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[1].name").value("Banana"));
    }
    @Test
    void getFruitById_withExistingId_shouldReturn200() throws Exception {
        FruitResponseDTO responseDTO = new FruitResponseDTO(1L, "Apple", 1.5);

        when(fruitService.getFruitById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightKg").value(1.5));
    }

    @Test
    void getFruitById_withNonExistingId_shouldReturn404() throws Exception {
        when(fruitService.getFruitById(99L)).thenThrow(new FruitNotFoundException(99L));

        mockMvc.perform(get("/fruits/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void updateFruit_withValidData_shouldReturn200() throws Exception {
        FruitRequestDTO requestDTO = new FruitRequestDTO("Mango", 2.0);
        FruitResponseDTO responseDTO = new FruitResponseDTO(1L, "Mango", 2.0);

        when(fruitService.updateFruit(any(Long.class), any(FruitRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mango"))
                .andExpect(jsonPath("$.weightKg").value(2.0));
    }

    @Test
    void updateFruit_withNonExistingId_shouldReturn404() throws Exception {
        FruitRequestDTO requestDTO = new FruitRequestDTO("Mango", 2.0);

        when(fruitService.updateFruit(any(Long.class), any(FruitRequestDTO.class)))
                .thenThrow(new FruitNotFoundException(99L));

        mockMvc.perform(put("/fruits/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateFruit_withInvalidData_shouldReturn400() throws Exception {
        FruitRequestDTO requestDTO = new FruitRequestDTO("", -1.0);

        mockMvc.perform(put("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void deleteFruit_withExistingId_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFruit_withNonExistingId_shouldReturn404() throws Exception {
        doThrow(new FruitNotFoundException(99L)).when(fruitService).deleteFruit(99L);

        mockMvc.perform(delete("/fruits/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}