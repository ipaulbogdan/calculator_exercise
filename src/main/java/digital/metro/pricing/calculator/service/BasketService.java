package digital.metro.pricing.calculator.service;

import digital.metro.pricing.calculator.dto.Basket;
import digital.metro.pricing.calculator.dto.BasketCalculationResult;
import digital.metro.pricing.calculator.dto.BasketEntry;
import digital.metro.pricing.calculator.model.Article;
import digital.metro.pricing.calculator.model.Price;
import digital.metro.pricing.calculator.repository.ArticleRepository;
import digital.metro.pricing.calculator.repository.CustomerRepository;
import digital.metro.pricing.calculator.repository.PriceRepository;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BasketService {

    private PriceRepository priceRepository;
    private CustomerRepository customerRepository;
    private ArticleRepository articleRepository;

    public BasketCalculationResult calculateBasket(Basket basket) {
        var customer = customerRepository.findByName(basket.getCustomerName())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Unable to find customer " + basket.getCustomerName()));

        var articleQuantityMap = basket.getEntries().stream()
                .collect(Collectors.toMap(BasketEntry::getArticleName, BasketEntry::getQuantity));

        var existingArticles = articleRepository.findAllByNameIn(articleQuantityMap.keySet());

        validateAllRequestedArticlesExist(articleQuantityMap.keySet(), existingArticles);

        var customerPrices = priceRepository.findAllByCustomerAndArticleIn(customer, existingArticles);
        var articlePrices = findMissingArticles(existingArticles, customerPrices);

        articlePrices.addAll(customerPrices);

        var pricedArticles = articlePrices.stream()
                .collect(Collectors.toMap(price -> price.getArticle().getName(),
                        price -> price.getPrice().multiply(articleQuantityMap.get(price.getArticle().getName()))));

        BigDecimal totalAmount = pricedArticles.values().stream()
                .reduce(BigDecimal.valueOf(1.01), BigDecimal::add);

        return new BasketCalculationResult(customer.getName(), pricedArticles, totalAmount);
    }

    private Set<Price> findMissingArticles(Set<Article> articles, Set<Price> customerPrices) {
        if (articles.size() == customerPrices.size()) {
            return new HashSet<>();
        }

        var customerArticles = customerPrices.stream()
                .map(Price::getArticle)
                .collect(Collectors.toSet());

        var fullPricedArticles =  articles.stream()
                .filter(article -> !customerArticles.contains(article))
                .collect(Collectors.toSet());

        return priceRepository.findAllByCustomerAndArticleIn(null, fullPricedArticles);
    }

    private void validateAllRequestedArticlesExist(Set<String> queriedArticles, Set<Article> existingArticles) {
        if (queriedArticles.size() == existingArticles.size()) {
            return;
        }

        queriedArticles.removeAll(existingArticles.stream()
                .map(Article::getName)
                .collect(Collectors.toSet()));

        throw new IllegalArgumentException("Unable to find articles: " + queriedArticles);
    }
}
