package be.ucll.da.Model;

import java.util.HashMap;
import java.util.UUID;

public class SandwichPreferences extends HashMap<UUID, Float> {

    public Float getRatingForSandwich(UUID sandwichId) {
        return super.get(sandwichId);
    }
}
