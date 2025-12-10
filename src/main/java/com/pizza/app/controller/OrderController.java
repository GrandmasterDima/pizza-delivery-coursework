package com.pizza.app.controller;

import com.pizza.app.entity.*;
import com.pizza.app.repository.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PizzaRepository pizzaRepository;
    // На майбутнє знадобиться репозиторій для деталей замовлення, щоб зберігати історію
    // private final OrderDetailRepository orderDetailRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        User client = userRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Order order = new Order();
        order.setClient(client);
        order.setDeliveryAddress(request.getAddress());
        order.setStatus(Order.OrderStatus.new_order);

        List<BigDecimal> prices = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Long pizzaId : request.getPizzaIds()) {
            Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow();
            prices.add(pizza.getPrice());
            total = total.add(pizza.getPrice());
        }

        if (prices.size() >= 11) {
            BigDecimal minPrice = prices.stream()
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            total = total.subtract(minPrice);
            System.out.println("Акція спрацювала! Знижка: " + minPrice);
        }
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }
    @Data
    public static class OrderRequest {
        private Long clientId;
        private String address;
        private List<Long> pizzaIds;
    }
}