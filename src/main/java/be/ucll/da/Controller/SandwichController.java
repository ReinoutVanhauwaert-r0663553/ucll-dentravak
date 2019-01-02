package be.ucll.da.Controller;

import be.ucll.da.Database.SandwichRepository;
import be.ucll.da.Model.Sandwich;
import be.ucll.da.Model.SandwichPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.naming.ServiceUnavailableException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Stream;

@RestController
public class SandwichController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private SandwichRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SandwichRepository sandwichRepository;

    /*@RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }*/

    @RequestMapping(value = "/sandwiches", method = RequestMethod.GET)
    public List<Sandwich> getSandwiches() {
        //return StreamSupport.stream(sandwichRepository.findAll().spliterator(), false).collect(Collectors.toList());
        try {
            SandwichPreferences preferences = getPreferences("0476832381");
            //TODO: sort allSandwiches by float in preferences
            List<Sandwich> allSandwiches = repository.findAll();
            allSandwiches = sortByPreferences(preferences,allSandwiches);
            return allSandwiches;
        } catch (Exception e) {
            return repository.findAll();
        }
    }

    public List<Sandwich> sortByPreferences(SandwichPreferences preferences, List<Sandwich> allSandwiches) {
        System.out.println(allSandwiches);
        Collections.sort(allSandwiches, (Sandwich s1, Sandwich s2) -> rating(preferences, s2).compareTo(rating(preferences, s1)));
        return allSandwiches;
    }

    private Float rating(SandwichPreferences preferences, Sandwich s2) {
        System.out.println(s2);
        System.out.println(preferences);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return preferences.getRatingForSandwich(s2.getId());
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
            //Sandwich oldSandwich = getSandwichesById(id);
            //oldSandwich = sandwich;
            return sandwichRepository.save(sandwich);
        }else{
            throw new IllegalArgumentException("don't do that... stop!");
        }
    }
    // why comment: for testing
    @GetMapping("/getpreferences/{emailAddress}")
    public SandwichPreferences getPreferences(@PathVariable String emailAddress) throws RestClientException, ServiceUnavailableException {
        URI service = recommendationServiceUrl()
                .map(s -> s.resolve("/recommend/" + emailAddress))
                .orElseThrow(ServiceUnavailableException::new);
        return restTemplate
                .getForEntity(service, SandwichPreferences.class)
                .getBody();
    }

    public Optional<URI> recommendationServiceUrl() {
        try {
            return Optional.of(new URI("http://localhost:8081"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
