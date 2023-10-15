package digital.metro.pricing.calculator.repository;

import digital.metro.pricing.calculator.model.Article;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByPublicId(UUID publicId);

    Optional<Article> findByName(String name);

    Set<Article> findAllByPublicIdIn(Set<UUID> publicIds);

    Set<Article> findAllByNameIn(Set<String> names);

}
