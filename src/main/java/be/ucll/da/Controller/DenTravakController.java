package be.ucll.da.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import be.ucll.da.Database.SandwichRepository;
import be.ucll.da.Model.Sandwich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DenTravakController {

    @Autowired
    private SandwichRepository sandwichrepository;

    @RequestMapping("/sandwiches")
    public List<Sandwich> getSandwiches() {
      // return new Sandwich("s",new BigDecimal(222),"ddd");
       // Iterable<Sandwich> sandwiches = sandwichrepository.findAll();
       // List<Sandwich> sandwhicsandwiches.forEach(Sandwich::);
        return StreamSupport.stream(sandwichrepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
}