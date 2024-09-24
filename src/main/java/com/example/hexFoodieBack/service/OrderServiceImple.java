package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.entity.*;
import com.example.hexFoodieBack.repository.*;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.request.OrderRequest;
import com.example.hexFoodieBack.response.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImple implements OrderService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CartService cartService;

    @Override
    public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest) {
        System.out.println(orderRequest.getEmail());
        User user=userRepository.findByEmail(orderRequest.getEmail());
        Address address1=new Address();
        address1.setState(orderRequest.getState());
        address1.setCity(orderRequest.getCity());
        address1.setZipcode(orderRequest.getZipcode());
        address1.setAddressLine1(orderRequest.getAddressLine1());
        address1.setAddressLine2(orderRequest.getAddressLine2());
        address1.setAddress_user(user);
        Address address=addressRepository.save(address1);
//        List<Address> addresses  = user.getAddress();
//        if(addresses==null){
//            addresses =new ArrayList<Address>();
//        }
//        addresses.add(address1);
        user.getAddress().add(address1);
        userRepository.save(user);
        Cart cart=cartService.findUserCart(user.getEmail());
        Restaurant restaurant=restaurantRepository.findByRestaurantId(orderRequest.getId());
        List<OrderItems> orderItems=new ArrayList<>();
        for(CartItems items:cart.getCartItems()){
            OrderItems orderItem=new OrderItems();
            orderItem.setPrice(items.getPrice());
            orderItem.setFood(items.getFood());
            orderItem.setQuantity(items.getQuantity());
            OrderItems createdItem=orderItemRepository.save(orderItem);
            orderItems.add(createdItem);
        }
        Order createOrder=new Order();
        createOrder.setUser(user);
        createOrder.setOrderItems(orderItems);
        createOrder.setTotalPrice(cart.getTotalPrice());
        createOrder.setOrderDate(LocalDateTime.now());
        createOrder.setOrderStatus("Placed");
        //createOrder.getPaymentDetails().setRazorpayPaymentStatus("Pending");
        createOrder.setAddress(address);
        createOrder.setRestaurant(restaurant);
        createOrder.setTotalItems(cart.getTotalItems());
        Order saveOrder=orderRepository.save(createOrder);
        for(OrderItems item:orderItems){
            item.setOrder(saveOrder);
            orderItemRepository.save(item);
        }

        List<CartItems> cartItems=cartItemRepository.findByEmail(orderRequest.getEmail());
        cart.setUser(null);
        cart.setTotalItems(null);
        cart.setTotalPrice(null);
        cartRepository.delete(cart);
        cartItems.clear();
        cartItemRepository.deleteAllInBatch(cartItems);
        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setMessage("Order Placed Successfully");
        orderResponse.setSuccess(true);
        String emailBody = "This is to notify you that your order is placed and soon will be delivered to you. Thank you for choosing Pomato!";
       // sendEmail(user.getEmail(),"Order Confirmation",emailBody);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Order>> getOrderData(EmailRequest emailRequest) {
        List<Order> order=orderRepository.findByUserEmail(emailRequest.getEmail());
        if(order==null){
            return null;
        }
        return new ResponseEntity<>(order,HttpStatus.OK);
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
