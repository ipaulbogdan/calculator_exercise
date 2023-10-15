package digital.metro.pricing.calculator.controller;

import digital.metro.pricing.calculator.dto.BasketCalculationResult;
import digital.metro.pricing.calculator.dto.Basket;
import digital.metro.pricing.calculator.service.BasketService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BasketController {

    private BasketService basketService;

    @PostMapping("/calculate-basket")
    public BasketCalculationResult calculateBasket(@RequestBody @Valid Basket basket) {
        return basketService.calculateBasket(basket);
    }
}
