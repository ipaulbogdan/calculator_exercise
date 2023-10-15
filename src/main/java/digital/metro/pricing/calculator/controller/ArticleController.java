package digital.metro.pricing.calculator.controller;

import digital.metro.pricing.calculator.dto.ArticleCreateDto;
import digital.metro.pricing.calculator.model.Article;
import digital.metro.pricing.calculator.service.ArticleService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("articles")
public class ArticleController {

    private ArticleService articleService;

    @PostMapping
    public Article createArticle(@RequestBody @Valid ArticleCreateDto articleCreateDto) {
        return articleService.create(articleCreateDto);
    }

    @GetMapping
    public List<Article> getAll() {
        return articleService.retrieveAll();
    }
}
