package digital.metro.pricing.calculator.repository;

import digital.metro.pricing.calculator.model.Article;
import digital.metro.pricing.calculator.model.Customer;
import digital.metro.pricing.calculator.model.Price;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    Optional<Price> findByCustomerAndArticle(Customer customer, Article article);

    Optional<Price> findByArticleAndCustomer(Article article, Customer customer);

    Set<Price> findAllByCustomerAndArticleIn(Customer customer, Set<Article> articles);
}
