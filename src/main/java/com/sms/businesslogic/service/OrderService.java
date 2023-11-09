package com.sms.businesslogic.service;

import com.sms.businesslogic.dto.OrderDTO;
import com.sms.businesslogic.dto.OrderPlaceDTO;
import com.sms.businesslogic.dto.OrderProductDTO;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.entity.OrderProduct;
import com.sms.businesslogic.entity.Product;
import com.sms.businesslogic.entity.User;
import com.sms.businesslogic.exception.OrderNotFoundException;
import com.sms.businesslogic.exception.ProductOutOfStockException;
import com.sms.businesslogic.repository.OrderProductRepository;
import com.sms.businesslogic.repository.OrderRepository;
import com.sms.businesslogic.repository.ProductRepository;
import com.sms.businesslogic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrdersByUserName(Integer userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Order> orders = user.getOrders();
        return orders.stream()
                .map(this::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO orderToOrderDTO(Order order) {
        List<OrderProduct> orderProducts = order.getOrderedProducts();
        List<OrderProductDTO> orderProductDTOs = orderProducts.stream()
                .map(this::orderProductToOrderProductDTO)
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .totalQuantity(order.getTotalQuantity())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .userID(order.getUser().getUserId())
                .orderedProducts(orderProductDTOs)
                .build();
    }

    private OrderProductDTO orderProductToOrderProductDTO(OrderProduct orderProduct) {
        return OrderProductDTO.builder()
                .id(orderProduct.getProdOrderId())
                .productName(orderProduct.getProduct().getProductName())
                .prodQuantity(orderProduct.getProdQuantity())
                .prodSubTotal(orderProduct.getProdTotalPrice())
                .build();
    }

    @Transactional
    public void createOrder(OrderPlaceDTO orderPlaceDTO, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        List<OrderProductDTO> orderProductDTOList = orderPlaceDTO.getOrderProducts();

        for (OrderProductDTO itemProduct : orderProductDTOList) {
            Product product = productRepository.findById(itemProduct.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not Found"));

            if (product.getQuantity() < itemProduct.getProdQuantity()) {
                throw new ProductOutOfStockException(product.getProductName() + " is currently out of stock");
            }
            int newQuantity = product.getQuantity() - itemProduct.getProdQuantity();
            product.setQuantity(newQuantity);
            productRepository.save(product);
        }

        Order order = new Order();
        order.setOrderDate(orderPlaceDTO.getOrderDate());
        order.setTotalQuantity(orderPlaceDTO.getTotalQuantity());
        order.setTotalPrice(orderPlaceDTO.getTotalPrice());
        order.setOrderStatus("processing");
        order.setUser(user);
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderProductDTO prodOrder : orderProductDTOList) {
            Product product = productRepository.findById(prodOrder.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProdQuantity(prodOrder.getProdQuantity());
            orderProduct.setProdTotalPrice(prodOrder.getProdSubTotal());
            orderProduct.setProduct(product);
            orderProduct.setOrder(order);
            orderProducts.add(orderProduct);
        }
        order.setOrderedProducts(orderProducts);
        orderRepository.save(order);
    }

    public String deleteOrder(Integer orderID) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() ->new OrderNotFoundException("Order with id "+orderID+" is not available"));

        List<OrderProduct> delOrderProducts = order.getOrderedProducts();
        for(int i=0;i<delOrderProducts.size();i++){
            OrderProduct itemProduct = delOrderProducts.get(i);
            Integer existingQuantity,inventoryQuantity,updatedQuantity,prodID;
            existingQuantity=itemProduct.getProdQuantity();
            prodID=itemProduct.getProduct().getProductId();
            Product product=productRepository.findById(prodID)
                    .orElseThrow(() ->new IllegalArgumentException("Product not found"));
            inventoryQuantity=product.getQuantity();
            updatedQuantity=inventoryQuantity+existingQuantity;
            product.setQuantity(updatedQuantity);
            productRepository.save(product);
        }
        orderRepository.deleteById(orderID);
        return "Order with id "+orderID+" deleted successfully";
    }

}
