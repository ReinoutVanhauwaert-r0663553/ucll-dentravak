package be.ucll.da.Database;

import be.ucll.da.Model.Sandwich;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SandwichRepository extends CrudRepository<Sandwich,UUID> {
    List<Sandwich> findAll();

}
