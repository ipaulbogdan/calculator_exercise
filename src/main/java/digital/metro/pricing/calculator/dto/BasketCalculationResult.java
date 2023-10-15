package digital.metro.pricing.calculator.dto;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasketCalculationResult {

    private String customer;
    private Map<String, BigDecimal> pricedBasketEntries;
    private BigDecimal totalAmount;
}
