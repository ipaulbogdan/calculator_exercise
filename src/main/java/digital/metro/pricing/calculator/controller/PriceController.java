package digital.metro.pricing.calculator.controller;

import digital.metro.pricing.calculator.dto.PriceCreateDto;
import digital.metro.pricing.calculator.model.Price;
import digital.metro.pricing.calculator.service.PriceService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("prices")
public class PriceController {

    private PriceService priceService;

    @PostMapping
    public Price createPrice(@RequestBody @Valid PriceCreateDto priceCreateDto) {
        return priceService.insertPrice(priceCreateDto);
    }

    @GetMapping
    public List<Price> getAll() {
        return priceService.retrieveAll();
    }

    @GetMapping("/article/{articleId}")
    public Price getArticlePrice(@PathVariable UUID articleId,
                                 @RequestParam(required = false) UUID customerId) {
        return priceService.retrievePrice(articleId, customerId);
    }
}
