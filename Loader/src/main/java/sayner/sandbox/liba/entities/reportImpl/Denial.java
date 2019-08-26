package sayner.sandbox.liba.entities.reportImpl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import sayner.sandbox.liba.entities.Report;

@AllArgsConstructor
public class Denial implements Report {

    @Setter
    private String message;

    @Override
    public String getMessage() {
        return String.format("Что-то там пошло не так: %s.", message);
    }

    @Override
    public String toString() {
        return "DENIED, cause: " + getMessage();
    }
}
