package com.sms.businesslogic.service;


import com.sms.businesslogic.dto.OrderPlaceDTO;
import com.sms.businesslogic.dto.OrderProductDTO;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.entity.OrderProduct;
import com.sms.businesslogic.entity.Product;
import com.sms.businesslogic.entity.User;
import com.sms.businesslogic.exception.OrderNotFoundException;
import com.sms.businesslogic.exception.ProdcutOutOfStockException;
import com.sms.businesslogic.repository.OrderRepository;
import com.sms.businesslogic.repository.ProductRepository;
import com.sms.businesslogic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void createOrder(OrderPlaceDTO orderPlaceDTO,String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        List<OrderProductDTO> orderProductDTOList =orderPlaceDTO.getOrderProducts();

        for(int i=0; i<orderProductDTOList.size();i++){
            OrderProductDTO itemProduct = orderProductDTOList.get(i);

            Product product = productRepository.findById(itemProduct.getId()).orElse(null);
            if(product != null){
                if(product.getQuantity() <= itemProduct.getProdQuantity()){
                    throw new ProdcutOutOfStockException(product.getProductName()+"is currently out of stock");
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

       orderRepository.deleteById(orderID);
       return "Order with id "+orderID+" deleted sucessfully";
    }
}
