package dev.sd.project.repository;

import dev.sd.project.model.Article;
import dev.sd.project.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article,String> {  //for manage data in database
    Article findByArticleId(String articleId);


}
