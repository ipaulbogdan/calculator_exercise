package digital.metro.pricing.calculator.it;

import static org.assertj.core.api.Assertions.assertThat;

import digital.metro.pricing.calculator.dto.Basket;
import digital.metro.pricing.calculator.dto.BasketEntry;
import digital.metro.pricing.calculator.service.BasketService;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BasketServiceIntegrationTest extends IntegrationTestBase {

    @Autowired
    private BasketService basketService;

    @Test
    void givenCustomerArticles_shouldUseCustomPrice() {
        var entries = Set.of(
                new BasketEntry().setArticleName("somon").setQuantity(BigDecimal.valueOf(4)),
                new BasketEntry().setArticleName("rib-eye").setQuantity(BigDecimal.valueOf(3)));

        var basket = new Basket().setCustomerName("Paul Idorasi").setEntries(entries);
        var basketResponse = basketService.calculateBasket(basket);

        assertThat(basketResponse.getCustomer()).isEqualTo("Paul Idorasi");
        assertThat(basketResponse.getPricedBasketEntries().get("rib-eye")).isEqualByComparingTo(BigDecimal.valueOf(60));
        assertThat(basketResponse.getPricedBasketEntries().get("somon")).isEqualByComparingTo(BigDecimal.valueOf(40));
        assertThat(basketResponse.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(101.01));
    }

    @Test
    void givenPartialCustomPrices_shouldFindRestAndCalculate() {
        var entries = Set.of(
                new BasketEntry().setArticleName("cartof").setQuantity(BigDecimal.valueOf(4)),
                new BasketEntry().setArticleName("inghetata").setQuantity(BigDecimal.valueOf(3)));

        var basket = new Basket().setCustomerName("Popescu Ion").setEntries(entries);
        var basketResponse = basketService.calculateBasket(basket);

        assertThat(basketResponse.getCustomer()).isEqualTo("Popescu Ion");

        assertThat(basketResponse.getPricedBasketEntries().get("cartof")).isEqualByComparingTo(BigDecimal.valueOf(10));
        assertThat(basketResponse.getPricedBasketEntries().get("inghetata"))
                .isEqualByComparingTo(BigDecimal.valueOf(2.67));

        assertThat(basketResponse.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(13.68));
    }

    @Test
    void givenNoCustomPrice_shouldCalculateFullPrice() {
        var entries = Set.of(
                new BasketEntry().setArticleName("somon").setQuantity(BigDecimal.valueOf(4)),
                new BasketEntry().setArticleName("rib-eye").setQuantity(BigDecimal.valueOf(3)));

        var basket = new Basket().setCustomerName("Popescu Ion").setEntries(entries);
        var basketResponse = basketService.calculateBasket(basket);

        assertThat(basketResponse.getCustomer()).isEqualTo("Popescu Ion");
        assertThat(basketResponse.getPricedBasketEntries().get("rib-eye")).isEqualByComparingTo(BigDecimal.valueOf(70.5));
        assertThat(basketResponse.getPricedBasketEntries().get("somon")).isEqualByComparingTo(BigDecimal.valueOf(50));
        assertThat(basketResponse.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(121.51));
    }


}
