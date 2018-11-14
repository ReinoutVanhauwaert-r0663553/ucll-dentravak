package be.ucll.da.Controller;

import be.ucll.da.Database.OrderRepository;
import be.ucll.da.Database.SandwichRepository;
import be.ucll.da.Model.Order;
import be.ucll.da.Model.Sandwich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderrepository;


    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public List<Order> getOrders() {

        return StreamSupport.stream(orderrepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public Order postOrders(@RequestBody Order order) {

        return orderrepository.save(order);
    }
}
