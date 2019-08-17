package sayner.sandbox.liba.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Airport {

    private String name;

    private Set<Plane> readyToFly;
}
