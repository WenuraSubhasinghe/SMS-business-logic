package com.sms.businesslogic.service;


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
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
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

       orderRepository.deleteById(orderID);
       return "Order with id "+orderID+" deleted sucessfully";
    }

   /* public void updateOrder(OrderPlaceDTO orderPlaceDTO, Integer orderID) {

        Order existingOrder = orderRepository.findById(orderID)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + orderID + " is not available"));

        List<OrderProductDTO> orderProductDTOLists =orderPlaceDTO.getOrderProducts();

        List<OrderProduct> existingOrderProds = existingOrder.getOrderedProducts();






        //checking the inventory is sufficient to process order
        for(int i=0; i<orderProductDTOLists.size();i++){
            OrderProductDTO itemProduct = orderProductDTOLists.get(i);

            Product product = productRepository.findById(itemProduct.getId()).orElse(null);
            if(product != null){
                if(product.getQuantity() <= itemProduct.getProdQuantity()){
                    throw new ProdcutOutOfStockException(product.getProductName()+"is currently out of stock");
                }
                else{
                    Integer newQuantity = 0;
                    Integer existingQuantity= product.getQuantity()+itemProduct.getProdQuantity();
                    newQuantity = product.getQuantity() - itemProduct.getProdQuantity();
                    product.setQuantity(newQuantity);
                }

            }
            else{
                throw new IllegalArgumentException("Product not Found");
            }
        }

        // Update order details
        existingOrder.setOrderDate(orderPlaceDTO.getOrderDate());
        existingOrder.setTotalQuantity(orderPlaceDTO.getTotalQuantity());
        existingOrder.setTotalPrice(orderPlaceDTO.getTotalPrice());
        existingOrder.setOrderStatus("processing");

        // Update the products for the order
        List<OrderProductDTO> orderProductDTOList = orderPlaceDTO.getOrderProducts();

        List<OrderProduct> updatedOrderProducts = new ArrayList<>();

        //setting products in OrderProduct entity
        for (OrderProductDTO prodOrder : orderProductDTOList) {
            Product product = productRepository.findById(prodOrder.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProdQuantity(prodOrder.getProdQuantity());
            orderProduct.setProdTotalPrice(prodOrder.getProdSubTotal());
            orderProduct.setProduct(product);
            orderProduct.setOrder(existingOrder);

            // Add product to the list of updated order products
            updatedOrderProducts.add(orderProduct);
        }

        // Set the updated products for the order
        existingOrder.setOrderedProducts(updatedOrderProducts);
        orderRepository.save(existingOrder);
    }*/

    /*public void updateOrder(OrderPlaceDTO orderPlaceDTO, Integer orderID) {
        // Retrieve the existing order based on the provided order ID
        Order existingOrder = orderRepository.findById(orderID)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + orderID + " is not available"));

        // Create a map to store updated product quantities
        Map<Integer, Integer> productQuantitiesToUpdate = new HashMap<>();

        // Gather existing product quantities for products associated with the order
        for (OrderProduct existingOrderProd : existingOrder.getOrderedProducts()) {
            Integer existingProductId = existingOrderProd.getProduct().getProductId();
            Integer existingProductQuantity = existingOrderProd.getProdQuantity();
            productQuantitiesToUpdate.put(existingProductId, existingProductQuantity);
        }

        // Check and update the product quantities in the database
        for (OrderProductDTO itemProduct : orderPlaceDTO.getOrderProducts()) {
            Product product = productRepository.findById(itemProduct.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

            Integer currentQuantity = product.getQuantity();
            Integer updatedQuantity = productQuantitiesToUpdate.getOrDefault(product.getProductId(), 0);

            // Check if the updated quantity exceeds the available product quantity
            if (currentQuantity < updatedQuantity + itemProduct.getProdQuantity()) {
                throw new ProdcutOutOfStockException(product.getProductName() + " is currently out of stock");
            }

            // Update the product quantity in the map
            productQuantitiesToUpdate.put(product.getProductId(), updatedQuantity + itemProduct.getProdQuantity());
        }

        // Update the product quantities in the Product table
        for (Map.Entry<Integer, Integer> entry : productQuantitiesToUpdate.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

            // Set the updated quantity and save it in the database
            product.setQuantity(entry.getValue());
            productRepository.save(product);
        }

        // Delete the existing OrderProduct entries associated with the provided order ID
        //orderProductRepository.deleteByOrder_Id(orderID);
        List<OrderProduct> listofOrders= orderProductRepository.findAllByOrder_OrderId(orderID);

        for(int i= 0;i<listofOrders.size();i++){
           Integer delID= listofOrders.get(i).getProdOrderId();
           orderProductRepository.deleteById(delID);
        }



        // Update the order details according to the provided OrderPlaceDTO
        existingOrder.setOrderDate(orderPlaceDTO.getOrderDate());
        existingOrder.setTotalQuantity(orderPlaceDTO.getTotalQuantity());
        existingOrder.setTotalPrice(orderPlaceDTO.getTotalPrice());
        existingOrder.setOrderStatus("processing");

        // Update the associated products for the order
        List<OrderProductDTO> orderProductDTOList = orderPlaceDTO.getOrderProducts();
        List<OrderProduct> updatedOrderProducts = new ArrayList<>();

        // Create new OrderProduct entries and associate them with the existing order
        for (OrderProductDTO prodOrder : orderProductDTOList) {
            Product product = productRepository.findById(prodOrder.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProdQuantity(prodOrder.getProdQuantity());
            orderProduct.setProdTotalPrice(prodOrder.getProdSubTotal());
            orderProduct.setProduct(product);
            orderProduct.setOrder(existingOrder);

            updatedOrderProducts.add(orderProduct);
        }

        // Set the updated OrderProduct entries for the existing order and save it
        existingOrder.setOrderedProducts(updatedOrderProducts);
        orderRepository.save(existingOrder);
    }*/

    public void updateOrder(OrderPlaceDTO orderPlaceDTO, Integer orderID) {
        // Retrieve the existing order based on the provided order ID
        Order existingOrder = orderRepository.findById(orderID)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + orderID + " is not available"));

        // Update the order details according to the provided OrderPlaceDTO
        existingOrder.setOrderDate(orderPlaceDTO.getOrderDate());
        existingOrder.setTotalQuantity(orderPlaceDTO.getTotalQuantity());
        existingOrder.setTotalPrice(orderPlaceDTO.getTotalPrice());
        existingOrder.setOrderStatus("processing");

        // Clear the existing OrderProduct list
        existingOrder.getOrderedProducts().clear();

        // Update the associated products for the order
        List<OrderProductDTO> orderProductDTOList = orderPlaceDTO.getOrderProducts();
        List<OrderProduct> updatedOrderProducts = new ArrayList<>();

        for (OrderProductDTO prodOrder : orderProductDTOList) {
            Product product = productRepository.findById(prodOrder.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProdQuantity(prodOrder.getProdQuantity());
            orderProduct.setProdTotalPrice(prodOrder.getProdSubTotal());
            orderProduct.setProduct(product);
            orderProduct.setOrder(existingOrder);

            updatedOrderProducts.add(orderProduct);
        }

        // Set the updated OrderProduct entries for the existing order
        existingOrder.setOrderedProducts(updatedOrderProducts);

        // Save the updated order along with the updated OrderProduct entries
        orderRepository.save(existingOrder);
    }




}
