package digital.metro.pricing.calculator.service;

import static java.lang.String.format;

import digital.metro.pricing.calculator.dto.PriceCreateDto;
import digital.metro.pricing.calculator.model.Article;
import digital.metro.pricing.calculator.model.Customer;
import digital.metro.pricing.calculator.model.Price;
import digital.metro.pricing.calculator.repository.ArticleRepository;
import digital.metro.pricing.calculator.repository.CustomerRepository;
import digital.metro.pricing.calculator.repository.PriceRepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PriceService {

    private PriceRepository priceRepository;
    private CustomerRepository customerRepository;
    private ArticleRepository articleRepository;

    public Price insertPrice(PriceCreateDto priceCreateDto) {
        var article = articleRepository.findByPublicId(priceCreateDto.getArticleId())
                .orElseThrow(() -> new EntityNotFoundException(
                        format("Unable to find article with id %s", priceCreateDto.getArticleId())));

        var customer = priceCreateDto.getCustomerId() == null
                ? null
                : customerRepository.findByPublicId(priceCreateDto.getCustomerId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                format("Unable to find customer with id %s", priceCreateDto.getCustomerId())));

        if (customer != null) {
            return createPriceForCustomer(article, customer, priceCreateDto);
        }

        var price = new Price()
                .setArticle(article)
                .setPrice(priceCreateDto.getPrice());

        return priceRepository.save(price);
    }

    private Price createPriceForCustomer(Article article, Customer customer, PriceCreateDto priceCreateDto) {
        var customerPrice = priceRepository.findByCustomerAndArticle(customer, article);

        if (customerPrice.isPresent()) {
            throw new IllegalArgumentException(format("Customer %s has preferential price for article %s",
                    customer.getName(),
                    article.getName()));
        }

        var price = new Price().setCustomer(customer).setPrice(priceCreateDto.getPrice()).setArticle(article);

        return priceRepository.save(price);
    }

    public List<Price> retrieveAll() {
        return priceRepository.findAll();
    }

    public Price retrievePrice(UUID articleId, UUID customerId) {
        var article = articleRepository.findByPublicId(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find article by provided id"));

        var customer = customerId == null ? null : customerRepository.findByPublicId(customerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        format("Unable to find customer with id %s", customerId)));

        return priceRepository.findByArticleAndCustomer(article, customer)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find price for article"));
    }
}
