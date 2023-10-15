package digital.metro.pricing.calculator.dto;

import java.math.BigDecimal;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceCreateDto {

    @NotNull
    private BigDecimal price;

    @NotNull
    private UUID articleId;

    private UUID customerId;


}
