package com.sms.businesslogic.service;


import com.sms.businesslogic.dto.OrderDTO;
import com.sms.businesslogic.dto.OrderPlaceDTO;
import com.sms.businesslogic.dto.OrderProductDTO;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.entity.OrderProduct;
import com.sms.businesslogic.entity.Product;
import com.sms.businesslogic.entity.User;
import com.sms.businesslogic.exception.OrderNotFoundException;
import com.sms.businesslogic.exception.ProdcutOutOfStockException;
import com.sms.businesslogic.repository.OrderProductRepository;
import com.sms.businesslogic.repository.OrderRepository;
import com.sms.businesslogic.repository.ProductRepository;
import com.sms.businesslogic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() ->new  IllegalArgumentException("User not found"));
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
                /*.paymentID(order.getPayment().getPaymentId())
                .deliveryID(order.getDelivery().getDeliveryId())*/
                .orderedProducts(orderProductDTOs)
                .build();
    }

    private OrderProductDTO orderProductToOrderProductDTO(OrderProduct orderProduct) {
        // Map OrderProduct entity to OrderProductDTO
        return OrderProductDTO.builder()
                .id(orderProduct.getProdOrderId())
                .productName(orderProduct.getProduct().getProductName()) // Change this according to your actual structure
                .prodQuantity(orderProduct.getProdQuantity())
                .prodSubTotal(orderProduct.getProdTotalPrice())
                .build();
    }

    public void createOrder(OrderPlaceDTO orderPlaceDTO,String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        List<OrderProductDTO> orderProductDTOList =orderPlaceDTO.getOrderProducts();

        //checking the inventory is sufficient to process order
        for(int i=0; i<orderProductDTOList.size();i++){
            OrderProductDTO itemProduct = orderProductDTOList.get(i);

            Product product = productRepository.findById(itemProduct.getId()).orElse(null);
            if(product != null){
                if(product.getQuantity() <= itemProduct.getProdQuantity()){
                    throw new ProdcutOutOfStockException(product.getProductName()+" is currently out of stock");
                }
                else{
                    Integer newQuantity = 0;
                    newQuantity = product.getQuantity() - itemProduct.getProdQuantity();
                    product.setQuantity(newQuantity);
                }

            }
            else{
                throw new IllegalArgumentException("Product not Found");
            }
        }

        Order order= new Order();
        order.setOrderDate(orderPlaceDTO.getOrderDate());
        order.setTotalQuantity(orderPlaceDTO.getTotalQuantity());
        order.setTotalPrice(orderPlaceDTO.getTotalPrice());
        order.setOrderStatus("processing");
        order.setUser(user);

        List<OrderProduct> orderProducts = new ArrayList<>();

        //setting products in OrderProduct entity
        for(int i = 0; i < orderProductDTOList.size();i++){
            OrderProductDTO prodOrder =orderProductDTOList.get(i);

            Product product = productRepository.findById(prodOrder.getId())
                    .orElseThrow(() ->new IllegalArgumentException("Product Not Found"));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProdQuantity(prodOrder.getProdQuantity());
            orderProduct.setProdTotalPrice(prodOrder.getProdSubTotal());
            orderProduct.setProduct(product);
            orderProduct.setOrder(order);

            //add product to arraylist
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
