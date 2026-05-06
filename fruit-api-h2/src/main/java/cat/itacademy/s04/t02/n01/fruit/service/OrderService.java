package cat.itacademy.s04.t02.n01.fruit.service;

import cat.itacademy.s04.t02.n01.fruit.dto.OrderItemDTO;
import cat.itacademy.s04.t02.n01.fruit.dto.OrderRequestDTO;
import cat.itacademy.s04.t02.n01.fruit.dto.OrderResponseDTO;
import cat.itacademy.s04.t02.n01.fruit.exception.OrderNotFoundException;
import cat.itacademy.s04.t02.n01.fruit.model.Order;
import cat.itacademy.s04.t02.n01.fruit.model.OrderItem;
import cat.itacademy.s04.t02.n01.fruit.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO requestDTO) {
        Order order = toEntity(requestDTO);
        Order savedOrder = orderRepository.save(order);
        return toResponseDTO(savedOrder);
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public OrderResponseDTO getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return toResponseDTO(order);
    }

    public OrderResponseDTO updateOrder(String id, OrderRequestDTO requestDTO) {
        orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        Order order = toEntity(requestDTO);
        order.setId(id);
        Order updatedOrder = orderRepository.save(order);
        return toResponseDTO(updatedOrder);
    }

    public void deleteOrder(String id) {
        orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.deleteById(id);
    }

    private Order toEntity(OrderRequestDTO requestDTO) {
        List<OrderItem> items = requestDTO.getItems()
                .stream()
                .map(item -> new OrderItem(item.getFruitName(), item.getQuantityInKilos()))
                .toList();
        return new Order(null, requestDTO.getClientName(), requestDTO.getDeliveryDate(), items);
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        List<OrderItemDTO> items = order.getItems()
                .stream()
                .map(item -> new OrderItemDTO(item.getFruitName(), item.getQuantityInKilos()))
                .toList();
        return new OrderResponseDTO(order.getId(), order.getClientName(), order.getDeliveryDate(), items);
    }
}