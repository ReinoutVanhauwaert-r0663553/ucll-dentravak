package be.ucll.da.Controller;

import be.ucll.da.Database.OrderRepository;
import be.ucll.da.Model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<Order> getOrders(@RequestParam(name = "date", required = false) String date) {
        List<Order> orders;

        if (date != null && !date.trim().isEmpty()) {
            orders = new ArrayList<Order>();
            String[] dateParts = date.split("-");

            for (Order order : orderRepository.findAll()) {
                if (order.getCreationDate().getYear() == Integer.parseInt(dateParts[0])
                        && order.getCreationDate().getMonthValue() == Integer.parseInt(dateParts[1])
                        && order.getCreationDate().getDayOfMonth() == Integer.parseInt(dateParts[2])) {
                    orders.add(order);
                }
            }
        } else {
            orders = StreamSupport.stream(orderRepository.findAll().spliterator(), false).collect(Collectors.toList());
        }

        return orders;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public Order postOrders(@RequestBody Order order) {
        order.setCreationDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Order ptOrderById(@PathVariable UUID id, @RequestBody Order order) {
        if(id.equals(order.getId())) {
            order.setPrinted(true);
            return orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("don't do that... stop!");
        }
    }

}
