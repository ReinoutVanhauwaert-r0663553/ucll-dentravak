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

    @RequestMapping(value = "/sandwiches", method = RequestMethod.GET)
    public List<Sandwich> getSandwiches(@RequestParam(name = "phoneNumber", required = false) String phoneNumber) {
        try {
            SandwichPreferences preferences = getPreferences(phoneNumber);
            List<Sandwich> allSandwiches = repository.findAll();
            allSandwiches = sortByPreferences(preferences, allSandwiches);
            return allSandwiches;
        } catch (Exception e) {
            System.out.println(e);
            return repository.findAll();
        }
    }
    
    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Sandwich getSandwichesById(@PathVariable UUID id) {
        return repository.findById(id).get();
    }

    @RequestMapping(value = "/sandwiches", method = RequestMethod.POST)
    @ResponseBody
    public Sandwich postSandwiches(@RequestBody Sandwich sandwich) {
        return repository.save(sandwich);
    }

    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Sandwich putSandwichesById(@PathVariable UUID id, @RequestBody Sandwich sandwich) {
        if(id.equals(sandwich.getId())) {
            return repository.save(sandwich);
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

   //  public Optional<URI> recommendationServiceUrl() {
   //      try {
   //          return Optional.of(new URI("http://localhost:8081"));
   //      } catch (URISyntaxException e) {
   //          throw new RuntimeException(e);
   //      }
   //  }

    public Optional<URI> recommendationServiceUrl() {
        return discoveryClient.getInstances("recommendation")
                .stream()
                .map(si -> si.getUri())
                .findFirst();
    }

    // Sort sandwiches by preferences
    public List<Sandwich> sortByPreferences(SandwichPreferences preferences, List<Sandwich> allSandwiches) {
        Collections.sort(allSandwiches, (Sandwich s1, Sandwich s2) -> rating(preferences, s2).compareTo(rating(preferences, s1)));
        return allSandwiches;
    }

    private Float rating(SandwichPreferences preferences, Sandwich s2) {
        return preferences.getRatingForSandwich(s2.getId());
    }
}
