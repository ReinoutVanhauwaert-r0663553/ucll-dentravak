package be.ucll.da.Database;

import be.ucll.da.Model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {

}
