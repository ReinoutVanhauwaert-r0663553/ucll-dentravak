package be.ucll.da.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.UUID;

@Entity
public class Sandwich {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private BigDecimal price;
    private String ingredienten;


    public  Sandwich(){

    }
    public Sandwich(String name, BigDecimal price, String ingredienten) {
        this.name = name;
        this.price = price;
        this.ingredienten = ingredienten;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIngredienten() {
        return ingredienten;
    }

    public void setIngredienten(String ingredienten) {
        this.ingredienten = ingredienten;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
