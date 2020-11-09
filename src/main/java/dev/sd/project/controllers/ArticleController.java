package dev.sd.project.controllers;

import dev.sd.project.model.Article;
import dev.sd.project.model.User;
import dev.sd.project.repository.ArticleRepository;
import dev.sd.project.repository.UserRepository;
import dev.sd.project.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import java.util.logging.Level;

@Log
@Controller
@RequestMapping("/article")
@AllArgsConstructor
public class ArticleController {
    private UserRepository userRepository;
    private ArticleRepository articleRepository;
    private ArticleService articleService;

    @GetMapping
    public ModelAndView articleView(@RequestParam(name = "id", required = false) Optional<String> articleId) {

        if (articleId.isPresent()) {
            Optional<Article> article = articleRepository.findById(articleId.get()); //เอาไว้อ่านค่า articleIdใน DB
            // Optional == check null

            if (article.isPresent()) {
                ModelAndView articleModel = new ModelAndView("article");
                articleModel.addObject(article.get());

                articleService.countArticleVisitor(article.get());

                return articleModel;
            }
        }

        return new ModelAndView("articleNotFound");
    }

    @PostMapping("/create")
    public RedirectView createArticle(String userId,
                                      String title,
                                      String content,
                                      String[] tag )  {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            log.log(Level.INFO, "User is null");

        }else {
            try {
                Article article = articleService.createArticle(user,title,content,new HashSet<>(Arrays.asList(tag)));
                return new RedirectView("/article?id="+article.getArticleId());

            }catch (Exception e) {}
        }


        return new RedirectView("/article/create");

}
    @GetMapping("/create")
    public ModelAndView createArticleForm(){
        return new ModelAndView("articleCreateForm");

    }


}
