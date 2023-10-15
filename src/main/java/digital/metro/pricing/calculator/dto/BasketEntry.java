package digital.metro.pricing.calculator.dto;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BasketEntry {

    private String articleName;
    private BigDecimal quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketEntry that = (BasketEntry) o;
        return Objects.equals(articleName, that.articleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleName);
    }
}
