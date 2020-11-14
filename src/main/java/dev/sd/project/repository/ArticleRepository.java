package dev.sd.project.repository;

import dev.sd.project.model.Article;
import dev.sd.project.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleRepository extends MongoRepository<Article,String> {  //for manage data in database
    Article findByArticleId(String articleId);
    List<Article> findByWriter(User user);
}
