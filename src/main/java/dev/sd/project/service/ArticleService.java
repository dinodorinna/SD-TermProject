package dev.sd.project.service;

import dev.sd.project.model.Article;
import dev.sd.project.model.User;
import dev.sd.project.repository.ArticleRepository;
import lombok.extern.java.Log;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Level;

@Service
@Log

public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createArticle(User writer, String title, String content) throws Exception {
        Article article = new Article();
        article.setWriter(writer);
        article.setTitle(title);
        article.setContent(content);
        if (articleRepository.exists(Example.of(article))){
            log.log(Level.INFO,"Duplicated Article");
            throw new Exception("Duplicated Article");
        }
        article.setPublishDate(new Date());
        article.setFavoriteCount(0);
        article.setVisitorCount(0);

        articleRepository.save(article);

    }
    

}
