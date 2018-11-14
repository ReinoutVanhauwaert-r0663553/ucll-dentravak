package be.ucll.da.Controller;

import be.ucll.da.Database.OrderRepository;
import be.ucll.da.Database.SandwichRepository;
import be.ucll.da.Model.Order;
import be.ucll.da.Model.Sandwich;
import org.apache.coyote.http2.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @RequestMapping(value = "/orders")
    public List<Order> postOrders(@RequestParam("date") String date) {
        List<Order> orders = new ArrayList<Order>();
        String[] dateparts = date.split("-");
        LocalDate givendate = LocalDate.of(Integer.parseInt(dateparts[0]),Integer.parseInt(dateparts[1]),Integer.parseInt(dateparts[2]));
        for (Order order: orderrepository.findAll()) {
            if(order.getOrderdate().equals(givendate)){
                orders.add(order);
            }
        }
        return orders;
    }
}
