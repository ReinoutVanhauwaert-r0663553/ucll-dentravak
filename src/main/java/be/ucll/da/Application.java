package be.ucll.da;

import be.ucll.da.Database.OrderRepository;
import be.ucll.da.Database.SandwichRepository;
import be.ucll.da.Model.BreadType;
import be.ucll.da.Model.Order;
import be.ucll.da.Model.Sandwich;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public CommandLineRunner SandwichesCommandLineRunner(SandwichRepository sandwichRepository){
        return (args) -> {
            sandwichRepository.save(new Sandwich("Martino",new BigDecimal(2.50),"Martino,Ansjovis,Augurk,Ajuin,PikanteSaus"));
            sandwichRepository.save(new Sandwich("Smos",new BigDecimal(2.50),"Kaas,Hesp,Sla,Tomaat,Eitjes,Worteltjes,Mayonaiss"));
            sandwichRepository.save(new Sandwich("Kaas",new BigDecimal(2.50),"Kaas,Sla,Tomaat,Eitjes,Worteltjes,Mayonaiss"));
            sandwichRepository.save(new Sandwich("Hesp",new BigDecimal(2.50),"Hesp,Sla,Tomaat,Eitjes,Worteltjes,Mayonaiss"));
            sandwichRepository.save(new Sandwich("Brie",new BigDecimal(2.50),"Brie"));


        };
    }
    @Bean
    public CommandLineRunner OrderCommandLineRunner(OrderRepository orderrepository){
        return (args) -> {
            orderrepository.save(new Order(new Sandwich("Martino",new BigDecimal(2.50),"Martino,Ansjovis,Augurk,Ajuin,PikanteSaus"), new Date(), BreadType.WRAP,"046848695"));
        };
    }
}