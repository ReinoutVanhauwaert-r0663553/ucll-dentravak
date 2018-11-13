package be.ucll.da.Model;

import be.ucll.da.Implementation.SandwichConverter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Convert(converter = SandwichConverter.class)
    private Sandwich sandwich;
    private Date orderdate;
    private BreadType breadType;
    private String phoneNumber;

    public Order() {}

    public Order(Sandwich sandwich, Date orderdate, BreadType breadType, String phoneNumber) {
        this.sandwich = sandwich;
        this.orderdate = orderdate;
        this.breadType = breadType;
        this.phoneNumber = phoneNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Sandwich getSandwich() {
        return sandwich;
    }

    public void setSandwich(Sandwich sandwich) {
        this.sandwich = sandwich;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public BreadType getBreadType() {
        return breadType;
    }

    public void setBreadType(BreadType breadType) {
        this.breadType = breadType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
