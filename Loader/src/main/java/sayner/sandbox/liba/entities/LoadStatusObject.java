package sayner.sandbox.liba.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoadStatusObject {

    private String message;
    private Boolean isSuccess;
}
