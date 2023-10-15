package digital.metro.pricing.calculator.service;

import digital.metro.pricing.calculator.dto.ArticleCreateDto;
import digital.metro.pricing.calculator.model.Article;
import digital.metro.pricing.calculator.repository.ArticleRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {

    private ArticleRepository articleRepository;

    public Article create(ArticleCreateDto articleCreateDto) {
        if (articleRepository.findByName(articleCreateDto.getArticleName()).isPresent()) {
            throw new IllegalArgumentException("Article already exists");
        }

        var article = new Article().setPublicId(UUID.randomUUID())
                .setName(articleCreateDto.getArticleName());

        return articleRepository.save(article);
    }

    public List<Article> retrieveAll() {
        return articleRepository.findAll();
    }
}
