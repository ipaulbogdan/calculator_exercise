package digital.metro.pricing.calculator.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import digital.metro.pricing.calculator.dto.Basket;
import digital.metro.pricing.calculator.dto.BasketEntry;
import digital.metro.pricing.calculator.model.Article;
import digital.metro.pricing.calculator.model.Customer;
import digital.metro.pricing.calculator.model.Price;
import digital.metro.pricing.calculator.repository.ArticleRepository;
import digital.metro.pricing.calculator.repository.CustomerRepository;
import digital.metro.pricing.calculator.repository.PriceRepository;
import digital.metro.pricing.calculator.service.BasketService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private BasketService basketService;

    @Test
    void givenUnknownCustomer_shouldThrowException() {
        var basket = new Basket().setCustomerName("Unknown Customer")
                .setEntries(Collections.emptySet());

        when(customerRepository.findByName("Unknown Customer")).thenReturn(Optional.empty());

        var throwable = assertThrows(EntityNotFoundException.class, () -> basketService.calculateBasket(basket));
        assertThat(throwable).hasMessage("Unable to find customer Unknown Customer");
    }

    @Test
    void givenUnknownArticles_shouldThrowException() {
        var basketEntries = Set.of(
                new BasketEntry().setArticleName("orange").setQuantity(BigDecimal.ONE),
                new BasketEntry().setArticleName("apple").setQuantity(BigDecimal.ONE),
                new BasketEntry().setArticleName("milk").setQuantity(BigDecimal.ONE),
                new BasketEntry().setArticleName("water").setQuantity(BigDecimal.ONE));

        var basket = new Basket().setCustomerName("Some Customer")
                .setEntries(basketEntries);

        when(customerRepository.findByName("Some Customer"))
                .thenReturn(Optional.of(new Customer().setName("Some Customer")));

        var existingArticles = Set.of(
                new Article().setName("orange"),
                new Article().setName("apple")
        );

        when(articleRepository.findAllByNameIn(any(Set.class))).thenReturn(existingArticles);


        var throwable = assertThrows(IllegalArgumentException.class, () -> basketService.calculateBasket(basket));
        assertThat(throwable).hasMessage("Unable to find articles: [milk, water]");
    }

    @Test
    void  givenAllCustomerPrices_shouldReturnCustomPrice() {
        var customer = new Customer().setName("Some Customer");

        var basketEntries = Set.of(
                new BasketEntry().setArticleName("orange").setQuantity(BigDecimal.ONE),
                new BasketEntry().setArticleName("apple").setQuantity(BigDecimal.valueOf(5)));

        var basket = new Basket().setCustomerName("Some Customer")
                .setEntries(basketEntries);

        when(customerRepository.findByName("Some Customer"))
                .thenReturn(Optional.of(customer));

        var orange = new Article().setName("orange");
        var apple = new Article().setName("apple");
        var existingArticles = Set.of(orange, apple);

        var customerPrices = Set.of(
                new Price().setCustomer(customer).setPrice(BigDecimal.TEN).setArticle(orange),
                new Price().setCustomer(customer).setPrice(BigDecimal.ONE).setArticle(apple));

        when(articleRepository.findAllByNameIn(any(Set.class))).thenReturn(existingArticles);
        when(priceRepository.findAllByCustomerAndArticleIn(customer, existingArticles)).thenReturn(customerPrices);

        var basketCalculationResult = basketService.calculateBasket(basket);

        assertThat(basketCalculationResult.getCustomer()).isEqualTo("Some Customer");

        assertThat(basketCalculationResult.getPricedBasketEntries().get("orange"))
                .isEqualByComparingTo(BigDecimal.TEN);

        assertThat(basketCalculationResult.getPricedBasketEntries().get("apple"))
                .isEqualByComparingTo(BigDecimal.valueOf(5));

        assertThat(basketCalculationResult.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(16.01));
    }

    @Test
    void  givenSomeCustomerPrices_shouldSearchForMissingPricesAndReturnTotal() {
        var customer = new Customer().setName("Some Customer");

        var basketEntries = Set.of(
                new BasketEntry().setArticleName("orange").setQuantity(BigDecimal.ONE),
                new BasketEntry().setArticleName("apple").setQuantity(BigDecimal.valueOf(5)),
                new BasketEntry().setArticleName("milk").setQuantity(BigDecimal.ONE));

        var basket = new Basket().setCustomerName("Some Customer")
                .setEntries(basketEntries);

        when(customerRepository.findByName("Some Customer"))
                .thenReturn(Optional.of(customer));

        var orange = new Article().setName("orange");
        var apple = new Article().setName("apple");
        var milk = new Article().setName("milk");
        var existingArticles = Set.of(orange, apple, milk);

        var customerPrices = Set.of(
                new Price().setCustomer(customer).setPrice(BigDecimal.TEN).setArticle(orange),
                new Price().setCustomer(customer).setPrice(BigDecimal.ONE).setArticle(apple));

        when(articleRepository.findAllByNameIn(any(Set.class))).thenReturn(existingArticles);
        when(priceRepository.findAllByCustomerAndArticleIn(customer, existingArticles)).thenReturn(customerPrices);

        var fullPricedArticle = new HashSet<Price>() {{
            add(new Price().setArticle(milk).setPrice(BigDecimal.valueOf(2.99)));
        }};

        when(priceRepository.findAllByCustomerAndArticleIn(null, Set.of(milk))).thenReturn(fullPricedArticle);

        var basketCalculationResult = basketService.calculateBasket(basket);

        assertThat(basketCalculationResult.getCustomer()).isEqualTo("Some Customer");

        assertThat(basketCalculationResult.getPricedBasketEntries().get("orange"))
                .isEqualByComparingTo(BigDecimal.TEN);

        assertThat(basketCalculationResult.getPricedBasketEntries().get("apple"))
                .isEqualByComparingTo(BigDecimal.valueOf(5));

        assertThat(basketCalculationResult.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(19));
    }

}
