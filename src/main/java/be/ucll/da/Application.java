package be.ucll.da;

import be.ucll.da.Database.OrderRepository;
import be.ucll.da.Database.SandwichRepository;
import be.ucll.da.Model.BreadType;
import be.ucll.da.Model.Order;
import be.ucll.da.Model.Sandwich;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry
               .addMapping("/**")
               .allowedOrigins("http://localhost:4200","http://localhost:8080")
               .allowedMethods("GET","PUT","POST","DELETE","OPTIONS");
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}