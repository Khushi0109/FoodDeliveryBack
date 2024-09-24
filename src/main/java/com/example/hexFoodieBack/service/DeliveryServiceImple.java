package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.entity.Order;
import com.example.hexFoodieBack.entity.User;
import com.example.hexFoodieBack.repository.OrderRepository;
import com.example.hexFoodieBack.repository.UserRepository;
import com.example.hexFoodieBack.request.DeliveryOrderRequest;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.request.StatusRequest;
import com.example.hexFoodieBack.response.StatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeliveryServiceImple implements DeliveryService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;
    @Override
    public ResponseEntity<StatusResponse> DeliveryState(StatusRequest statusRequest) {
        Order order=orderRepository.findByOrderId(statusRequest.getId());
        User user=userRepository.findByEmail(statusRequest.getEmail());
        order.setOrderStatus("Picked Up");
        order.setDelivery(user);
        orderRepository.save(order);
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setSuccess(true);
        statusResponse.setMessage("Order Picked");
        String emailBody = "This is to notify you that your order is Picked up by our delivery partner";
        //sendEmail(user.getEmail(),"Order Delivered",emailBody);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Order>> getdeliveryOrders(DeliveryOrderRequest deliveryOrderRequest) {
        List<Order> orders=orderRepository.getDeliveryOrders(deliveryOrderRequest.getArea());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Order>> getPickedOrders(EmailRequest emailRequest) {
        List<Order> orders=orderRepository.getPickedOrders(emailRequest.getEmail());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StatusResponse> markDelivered(StatusRequest statusRequest) {
        Order order=orderRepository.findByOrderId(statusRequest.getId());
        User user=userRepository.findByEmail(statusRequest.getEmail());
        order.setOrderStatus("Delivered");
        orderRepository.save(order);
        StatusResponse statusResponse=new StatusResponse();
        statusResponse.setSuccess(true);
        statusResponse.setMessage("Order Delivered");
        String emailBody = "This is to notify you that your order is Delivered, enjoy your meal!";
        //sendEmail(user.getEmail(),"Order Delivered",emailBody);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);

    }

    private void sendEmail(String to,String subject,String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        log.info("fddf");
        javaMailSender.send(mailMessage);
    }
}
