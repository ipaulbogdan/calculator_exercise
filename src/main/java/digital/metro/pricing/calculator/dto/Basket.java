package digital.metro.pricing.calculator.dto;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Basket {

    @NotBlank
    private String customerName;
    private Set<BasketEntry> entries;

}
