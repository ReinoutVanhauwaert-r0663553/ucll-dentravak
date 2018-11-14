package be.ucll.da.Controller;

import be.ucll.da.Database.SandwichRepository;
import be.ucll.da.Model.Sandwich;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class SandwichController {

    @Autowired
    private SandwichRepository sandwichrepository;

    @RequestMapping(value = "/sandwiches", method = RequestMethod.GET)
    public List<Sandwich> getSandwiches() {

        return StreamSupport.stream(sandwichrepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @RequestMapping(value = "/sandwiches", method = RequestMethod.POST)
    @ResponseBody
    public Sandwich postSandwiches(@RequestBody Sandwich sandwich) {

        return sandwichrepository.save(sandwich);
        //return new Sandwich(sandwich);
    }
    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Sandwich getSandwichesById(@PathVariable UUID id) {

        return sandwichrepository.findById(id).get();
        //return new Sandwich(sandwich);
    }
    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Sandwich putSandwichesById(@PathVariable UUID id,@RequestBody Sandwich sandwich) {
        if(id.equals(sandwich.getId())) {
            Sandwich oldsandwich = getSandwichesById(id);
            oldsandwich = sandwich;
            return sandwichrepository.save(oldsandwich);
        }else{
            throw new IllegalArgumentException("don't do that... stop!");
        }
        //return new Sandwich(sandwich);
    }
}
