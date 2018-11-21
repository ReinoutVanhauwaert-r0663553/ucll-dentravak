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
    private SandwichRepository sandwichRepository;

    @RequestMapping(value = "/sandwiches", method = RequestMethod.GET)
    public List<Sandwich> getSandwiches() {
        return StreamSupport.stream(sandwichRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @RequestMapping(value = "/sandwiches", method = RequestMethod.POST)
    @ResponseBody
    public Sandwich postSandwiches(@RequestBody Sandwich sandwich) {
        return sandwichRepository.save(sandwich);
    }
    
    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Sandwich getSandwichesById(@PathVariable UUID id) {
        return sandwichRepository.findById(id).get();
    }

    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Sandwich putSandwichesById(@PathVariable UUID id,@RequestBody Sandwich sandwich) {
        if(id.equals(sandwich.getId())) {
            Sandwich oldSandwich = getSandwichesById(id);
            oldSandwich = sandwich;
            return sandwichRepository.save(oldSandwich);
        }else{
            throw new IllegalArgumentException("don't do that... stop!");
        }
    }
}
