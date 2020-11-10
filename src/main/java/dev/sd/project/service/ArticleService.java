package dev.sd.project.service;

import dev.sd.project.model.Article;
import dev.sd.project.model.User;
import dev.sd.project.repository.ArticleRepository;
import lombok.extern.java.Log;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.Set;
import java.util.logging.Level;

@Service
@Log
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(User writer, String title,String description, String content, Set<String> tag) throws Exception {
        Article article = new Article();
        article.setWriter(writer);
        article.setTitle(title);
        article.setDescription(description);
        article.setContent(content);
        if (articleRepository.exists(Example.of(article))){
            log.log(Level.INFO,"Duplicated Article");
            throw new Exception("Duplicated Article");
        }
        article.setPublishDate(new Date());
        article.setFavoriteCount(0);
        article.setVisitorCount(0);
        article.setTag(tag);

        articleRepository.save(article);

        return article ;

    }

    public Article editArticle(String articleId, String title,String description, String content, Set<String> tag) throws Exception {
        Article article = articleRepository.findByArticleId(articleId);
        if (article == null){
            log.log(Level.INFO,"Article Not Found");
            throw new Exception("Article Not Found");
        }

        article.setTitle(title);
        article.setDescription(description);
        article.setContent(content);
        article.setEditDate(new Date());
        article.setTag(tag);

        articleRepository.save(article);
        return article;

    }

    public void deleteArticle(String articleId) throws Exception {
        Article article = articleRepository.findByArticleId(articleId);
        if (article == null){
            log.log(Level.INFO,"Article Not Found");
            throw new Exception("Article Not Found");
        }
        articleRepository.delete(article);

    }

    public void countArticleVisitor(Article article){
        article.setVisitorCount(article.getVisitorCount()+1);
        articleRepository.save(article);
    }


    

}
